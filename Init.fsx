#r "nuget: sqlite-net-pcl, 1.9.172"
#r "nuget: FSharp.Data, 6.4.1"

open System.Collections.Generic
open System.IO
open System.Net
open System.Net.Http
open System.Text.Json.Serialization
open SQLite
open FSharp.Data

type Pokedex = HtmlProvider<"https://www.pokemon.com/us/pokedex/venusaur">

// Create a new SQLite database
let dbFile = "snapdex.db"

if File.Exists(dbFile) then
    File.Delete(dbFile)

let db = new SQLiteConnection(dbFile)

db.Execute("""
CREATE TABLE Categories(
    id INTEGER PRIMARY KEY NOT NULL
);
""")

db.Execute("""
CREATE TABLE Abilities(
    id INTEGER PRIMARY KEY NOT NULL
);
""")

db.Execute("""
CREATE TABLE Pokemons(
    id INTEGER PRIMARY KEY NOT NULL,
    weight REAL NOT NULL,
    height REAL NOT NULL,
    categoryId INTEGER NOT NULL,
    abilityId INTEGER NOT NULL,
    maleToFemaleRatio REAL NOT NULL,
    FOREIGN KEY(categoryId) REFERENCES Categories(id),
    FOREIGN KEY(abilityId) REFERENCES Abilities(id)
);
""")

db.Execute("""
CREATE TABLE PokemonTypes(
    pokemonTypeId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    pokemonId INTEGER NOT NULL,
    type INTEGER NOT NULL,
    FOREIGN KEY(pokemonId) REFERENCES Pokemons(id)
);
""")

db.Execute("""
CREATE TABLE PokemonWeaknesses(
    pokemonWeaknessId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    pokemonId INTEGER NOT NULL,
    type INTEGER NOT NULL,
    FOREIGN KEY(pokemonId) REFERENCES Pokemons(id)
);
""")

db.Execute("""
CREATE TABLE CategoryTranslations(
    categoryTranslationId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    categoryId INTEGER NOT NULL,
    language TEXT NOT NULL,
    name TEXT NOT NULL,
    FOREIGN KEY(categoryId) REFERENCES Categories(id)
);
""")

db.Execute("""
CREATE TABLE AbilityTranslations(
    abilityTranslationId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    abilityId INTEGER NOT NULL,
    language TEXT NOT NULL,
    name TEXT NOT NULL,
    FOREIGN KEY(abilityId) REFERENCES Abilities(id)
);
""")

db.Execute("""
CREATE TABLE PokemonTranslations(
    pokemonTranslationId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    pokemonId INTEGER NOT NULL,
    language TEXT NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    FOREIGN KEY(pokemonId) REFERENCES Pokemons(id)
);
""")

// Pull data from Internet
let pokemonUrl number = $"https://pokeapi.co/api/v2/pokemon/{number}"
let largeImageLink (name: string) =
    let n =
        if name = "mr-mime" then
            "mr.mime"
        else
            name.Replace("-", "_")
    $"https://projectpokemon.org/images/normal-sprite/{n}.gif"

let largeImageFileName number = $"pokemon_{number:D4}_large.gif"
let mediumImageFileName number = $"pokemon_{number:D4}_medium.png"

type Ability =
    { name: string }

type PokemonAbility =
    { ability: Ability }
    
type Type =
    { name: string }
    
type PokemonType =
    { [<JsonPropertyName("type")>]type_: Type }
    
type PokemonSprites =
    { front_default: string }

type PokemonData =
    { id: int
      name: string
      height: int
      weight: int
      abilities: PokemonAbility list
      types: PokemonType list
      sprites: PokemonSprites }
    
let download (client: HttpClient) (link: string) (filename: string) =
    task {        
        try
            use! stream = client.GetStreamAsync(link)
            
            // Copy to Compose project
            let path = Path.Combine("Snapdex-Compose/app/src/main/res/drawable", filename)
            use writer = new StreamWriter(path, false)
            stream.CopyTo(writer.BaseStream)
        with ex ->
            printfn $"Error downloading %s{link} : %s{filename}"
    }
    
let copyDatabase () =
    let path = Path.Combine("Snapdex-Compose/app/src/main/assets", dbFile)
    File.Copy(dbFile, path, true)
    File.Delete(dbFile)
    
task {
    try
        let abilities = Dictionary<string, int>()
        let categories = Dictionary<string, int>()

        let clientHandler = new HttpClientHandler(UseCookies = true, CookieContainer = CookieContainer())
        let client = new HttpClient(clientHandler)
        client.DefaultRequestHeaders.Add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
        
        for number = 1 to 151 do
            // Insert data into database
            let! response = client.GetAsync(pokemonUrl number)
            let! content = response.Content.ReadAsStringAsync()
            let data = System.Text.Json.JsonSerializer.Deserialize<PokemonData>(content)
            
            let abilityName = data.abilities.Head.ability.name
            let abilityId =
                if abilities.ContainsKey(abilityName) then
                    abilities[abilityName]
                else
                    let id = abilities.Count + 1
                    abilities.Add(abilityName, id)
                    db.Execute("INSERT INTO Abilities(id) VALUES(?)", id) |> ignore
                    db.Execute("INSERT INTO AbilityTranslations(abilityId, language, name) VALUES(?, ?, ?)", id, "en", abilityName) |> ignore
                    id
                    
            let categoryName = data.abilities.Head.ability.name
            let categoryId =
                if categories.ContainsKey(categoryName) then
                    categories[categoryName]
                else
                    let id = categories.Count + 1
                    categories.Add(categoryName, id)
                    db.Execute("INSERT INTO Categories(id) VALUES(?)", id) |> ignore
                    db.Execute("INSERT INTO CategoryTranslations(categoryId, language, name) VALUES(?, ?, ?)", id, "en", categoryName) |> ignore
                    id
                    
            let html = Pokedex.Load($"https://ph.portal-pokemon.com/play/pokedex/{number:D4}")
            let name = html.Html.CssSelect(".pokemon-slider__main-name").Head.InnerText()
            let description = html.Html.CssSelect(".pokemon-story__body span").Head.InnerText()
                    
            db.Execute("INSERT INTO Pokemons(id, weight, height, categoryId, abilityId, maleToFemaleRatio) VALUES(?, ?, ?, ?, ?, ?)", data.id, data.weight, data.height, categoryId, abilityId, 0.875) |> ignore
            db.Execute("INSERT INTO PokemonTypes(pokemonId, type) VALUES(?, ?)", data.id, 0) |> ignore
            db.Execute("INSERT INTO PokemonWeaknesses(pokemonId, type) VALUES(?, ?)", data.id, 0) |> ignore
            db.Execute("INSERT INTO PokemonTranslations(pokemonId, language, name, description) VALUES(?, ?, ?, ?)", data.id, "en", name, description) |> ignore
            
            printfn $"Pokemon %d{number}: %s{name}"
            
            // Download images
            do! download client (largeImageLink data.name) (largeImageFileName number)
            do! download client data.sprites.front_default (mediumImageFileName number)
            
        copyDatabase()
        
        printfn "Done"
    with ex ->
        printfn $"Error: %s{ex.Message}"
}