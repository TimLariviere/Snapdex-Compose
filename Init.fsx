#r "nuget: sqlite-net-pcl, 1.9.172"

open System.Collections.Generic
open System.IO
open System.Net
open System.Net.Http
open System.Text.Json.Serialization
open System.Threading.Tasks
open SQLite

// Create a new SQLite database
let dbFile: string = "snapdex.db"

if File.Exists(dbFile) then
    File.Delete(dbFile)

let db = new SQLiteConnection(dbFile)

db.Execute("""
CREATE TABLE Categories(
    id INTEGER PRIMARY KEY NOT NULL
);
""")

db.Execute("""
CREATE TABLE CategoryTranslations(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    categoryId INTEGER NOT NULL,
    language TEXT NOT NULL,
    name TEXT NOT NULL,
    FOREIGN KEY(categoryId) REFERENCES Categories(id)
);
""")

db.Execute("""
CREATE TABLE Abilities(
    id INTEGER PRIMARY KEY NOT NULL
);
""")

db.Execute("""
CREATE TABLE AbilityTranslations(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    abilityId INTEGER NOT NULL,
    language TEXT NOT NULL,
    name TEXT NOT NULL,
    FOREIGN KEY(abilityId) REFERENCES Abilities(id)
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
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    pokemonId INTEGER NOT NULL,
    type INTEGER NOT NULL,
    FOREIGN KEY(pokemonId) REFERENCES Pokemons(id)
);
""")

db.Execute("""
CREATE TABLE PokemonTranslations(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    pokemonId INTEGER NOT NULL,
    language TEXT NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    FOREIGN KEY(pokemonId) REFERENCES Pokemons(id)
);
""")

db.Execute("""
CREATE TABLE EvolutionChains(
    id INTEGER PRIMARY KEY NOT NULL,
    startingPokemonId INTEGER NOT NULL,
    FOREIGN KEY(startingPokemonId) REFERENCES Pokemons(id)
);
""")

db.Execute("""
CREATE TABLE EvolutionChainLinks(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    evolutionChainId INTEGER NOT NULL,
    pokemonId INTEGER NOT NULL,
    minLevel INTEGER NOT NULL,
    FOREIGN KEY(evolutionChainId) REFERENCES EvolutionChains(id),
    FOREIGN KEY(pokemonId) REFERENCES Pokemons(id)
);
""")

db.Execute("""
CREATE TABLE Users(
    id TEXT PRIMARY KEY NOT NULL,
    avatarId INTEGER NOT NULL,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    createdAt INTEGER NOT NULL,
    updatedAt INTEGER NOT NULL
)
""")

db.Execute("""
CREATE TABLE UserPokemons(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    userId TEXT NOT NULL,
    pokemonId INTEGER NOT NULL,
    createdAt INTEGER NOT NULL,
    updatedAt INTEGER NOT NULL,
    FOREIGN KEY(userId) REFERENCES Users(id),
    FOREIGN KEY(pokemonId) REFERENCES Pokemons(id)
)
""")

// Pull data from Internet
let allowedLanguages = [ "en"; "fr" ]

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
    { name: string
      url: string }

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
            let path = Path.Combine("ui/src/main/res/drawable", filename)
            use writer = new StreamWriter(path, false)
            stream.CopyTo(writer.BaseStream)
        with ex ->
            printfn $"Error downloading %s{link} : %s{filename}"
    }
    
let copyDatabase () =
    let path = Path.Combine("app/src/main/assets", dbFile)
    File.Copy(dbFile, path, true)
    File.Delete(dbFile)
    
let typeToInt(typ: string) =
    match typ with
    | "bug" -> 0
    | "dragon" -> 1
    | "electric" -> 2
    | "fairy" -> 3
    | "fighting" -> 4
    | "fire" -> 5
    | "flying" -> 6
    | "ghost" -> 7
    | "grass" -> 8
    | "ground" -> 9
    | "ice" -> 10
    | "normal" -> 11
    | "poison" -> 12
    | "psychic" -> 13
    | "rock" -> 14
    | "steel" -> 15
    | "water" -> 16
    | _ -> 0
    
    
let abilities = ResizeArray<int>()
let categories = Dictionary<string, int>()
    
module Abilities =
    type Language =
        { name: string }
        
    type Name =
        { name: string
          language: Language }
    
    type Data =
        { names: Name list }
    
    let run (db: SQLiteConnection) (client: HttpClient) (abilityId: int) =
        task {
            let! response = client.GetAsync($"https://pokeapi.co/api/v2/ability/{abilityId}/")
            let! content = response.Content.ReadAsStringAsync()
            let data = System.Text.Json.JsonSerializer.Deserialize<Data>(content)
            
            if abilities.Contains(abilityId) then
                return ()
            else
                abilities.Add(abilityId)
                db.Execute("INSERT INTO Abilities(id) VALUES(?)", abilityId) |> ignore
            
                for lang in allowedLanguages do
                    let name = data.names |> List.tryFind(fun x -> x.language.name = lang) |> Option.map _.name |> Option.defaultValue ""
                    db.Execute("INSERT INTO AbilityTranslations(abilityId, language, name) VALUES(?, ?, ?)", abilityId, lang, name) |> ignore
        }
        
module Categories =
    type Language =
        { name: string }
        
    type Genus =
        { genus: string
          language: Language }
    
    type Data =
        { genera: Genus list }
        
    let mutable categoryId = 0
    let categories = Dictionary<string, int>()
    
    let run (db: SQLiteConnection) (client: HttpClient) (number: int) =
        task {
            let! response = client.GetAsync($"https://pokeapi.co/api/v2/pokemon-species/{number}/")
            let! content = response.Content.ReadAsStringAsync()
            let data = System.Text.Json.JsonSerializer.Deserialize<Data>(content)
            
            let englishGenus = data.genera |> List.tryFind(fun x -> x.language.name = "en") |> Option.map _.genus |> Option.defaultValue ""
            if categories.ContainsKey(englishGenus) then
                return categories[englishGenus]
            else
                categoryId <- categories.Count + 1
                categories.Add(englishGenus, categoryId)
                db.Execute("INSERT INTO Categories(id) VALUES(?)", categoryId) |> ignore
                
                for lang in allowedLanguages do
                    let name = data.genera |> List.tryFind(fun x -> x.language.name = lang) |> Option.map _.genus |> Option.defaultValue ""
                    let name = name.Replace(" Pokémon", "")
                    let name = name.Replace("Pokémon ", "")
                    db.Execute("INSERT INTO CategoryTranslations(categoryId, language, name) VALUES(?, ?, ?)", categoryId, lang, name) |> ignore
                    
                return categoryId
        }
    
module PokemonTranslations =
    type Language =
        { name: string }
        
    type Name =
        { name: string
          language: Language }
        
    type FlavorTextEntry =
        { flavor_text: string
          language: Language }
        
    type Data =
        { names: Name list
          flavor_text_entries: FlavorTextEntry list }
        
    let run (db: SQLiteConnection) (client: HttpClient) (pokemonNumber: int) =
        task {
            let! response = client.GetAsync($"https://pokeapi.co/api/v2/pokemon-species/{pokemonNumber}/")
            let! content = response.Content.ReadAsStringAsync()
            let data = System.Text.Json.JsonSerializer.Deserialize<Data>(content)
            
            for lang in allowedLanguages do
                let name = data.names |> List.tryFind(fun x -> x.language.name = lang) |> Option.map _.name |> Option.defaultValue ""
                let description = data.flavor_text_entries |> List.tryFind(fun x -> x.language.name = lang) |> Option.map _.flavor_text |> Option.defaultValue ""
                let description = description.Replace("\n", " ")
                db.Execute("INSERT INTO PokemonTranslations(pokemonId, language, name, description) VALUES(?, ?, ?, ?)", pokemonNumber, lang, name, description) |> ignore
        }
        
module EvolutionChains =
    type Species =
        { url: string }
        
    type EvolutionDetails =
        { min_level: int option }
    
    type Chain =
        { species: Species
          evolves_to: Chain list
          evolution_details: EvolutionDetails list }
    
    type Data =
        { chain: Chain }
    
    let rec processChain (db: SQLiteConnection) (client: HttpClient) (evolutionChainId: int) (chain: Chain) =
        task {
            let pokemonId = chain.species.url.Replace("https://pokeapi.co/api/v2/pokemon-species/", "").Replace("/", "") |> int
            if pokemonId <= 151 then
                let minLevel = if chain.evolution_details.Length > 0 then chain.evolution_details.Head.min_level else None
                let minLevel = minLevel |> Option.defaultValue 0
                db.Execute("INSERT INTO EvolutionChainLinks(evolutionChainId, pokemonId, minLevel) VALUES(?, ?, ?)", evolutionChainId, pokemonId, minLevel) |> ignore
                
                for chain in chain.evolves_to do
                    do! processChain db client evolutionChainId chain
        }
    
    let run (db: SQLiteConnection) (client: HttpClient) (evolutionChainNumber: int) =
        task {
            let! response = client.GetAsync($"https://pokeapi.co/api/v2/evolution-chain/{evolutionChainNumber}/")
            let! content = response.Content.ReadAsStringAsync()            
            let data = System.Text.Json.JsonSerializer.Deserialize<Data>(content)
            
            printfn $"Evolution chain %d{evolutionChainNumber}"
            
            let startingPokemonId = data.chain.species.url.Replace("https://pokeapi.co/api/v2/pokemon-species/", "").Replace("/", "") |> int
            if startingPokemonId <= 151 then
                db.Execute("INSERT INTO EvolutionChains(id, startingPokemonId) VALUES(?, ?)", evolutionChainNumber, startingPokemonId) |> ignore
                
                for chain in data.chain.evolves_to do
                    do! processChain db client evolutionChainNumber chain
            else
                let startingPokemonId = data.chain.evolves_to.Head.species.url.Replace("https://pokeapi.co/api/v2/pokemon-species/", "").Replace("/", "") |> int
                db.Execute("INSERT INTO EvolutionChains(id, startingPokemonId) VALUES(?, ?)", evolutionChainNumber, startingPokemonId) |> ignore
                
                for chain in data.chain.evolves_to.Head.evolves_to do
                    do! processChain db client evolutionChainNumber chain
        }
    
task {
    try
        let clientHandler = new HttpClientHandler(UseCookies = true, CookieContainer = CookieContainer())
        let client = new HttpClient(clientHandler)
        client.DefaultRequestHeaders.Add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
        
        for pokemonNumber = 1 to 151 do
            // Insert data into databaseA
            let! response = client.GetAsync($"https://pokeapi.co/api/v2/pokemon/{pokemonNumber}")
            let! content = response.Content.ReadAsStringAsync()
            let data = System.Text.Json.JsonSerializer.Deserialize<PokemonData>(content)
            printfn $"Pokemon %d{pokemonNumber}: %s{data.name}"
            
            // Insert abilities
            let abilityId = data.abilities.Head.ability.url.Replace("https://pokeapi.co/api/v2/ability/", "").Replace("/", "") |> int
            do! Abilities.run db client abilityId
            
            // Insert categories
            let! categoryId = Categories.run db client pokemonNumber
                    
            // Insert pokemon
            db.Execute("INSERT INTO Pokemons(id, weight, height, categoryId, abilityId, maleToFemaleRatio) VALUES(?, ?, ?, ?, ?, ?)", data.id, data.weight, data.height, categoryId, abilityId, 0.875) |> ignore
            
            // Insert types
            let filteredTypes = data.types |> List.filter(fun x -> x.type_.name <> "dark") // dark is gen 2+ only
            for t in filteredTypes do
                let typeId = typeToInt t.type_.name
                db.Execute("INSERT INTO PokemonTypes(pokemonId, type) VALUES(?, ?)", data.id, typeId) |> ignore
            
            do! PokemonTranslations.run db client pokemonNumber
            
            // Download images
            do! download client (largeImageLink data.name) (largeImageFileName pokemonNumber)
            do! download client data.sprites.front_default (mediumImageFileName pokemonNumber)
            
        for evolutionChainNumber = 1 to 78 do
            do! EvolutionChains.run db client evolutionChainNumber
            
        copyDatabase()
        
        printfn "Done"
    with ex ->
        printfn $"Error: %s{ex.ToString()}"
}
|> _.GetAwaiter().GetResult()