#r "nuget: sqlite-net-pcl, 1.9.172"

open System.IO
open System.Net.Http
open System.Text.Json.Serialization
open SQLite

// Create a new SQLite database
let dbFile = "snapdex.db"
let imagesDir = "images"

if File.Exists(dbFile) then
    File.Delete(dbFile)
    
if File.Exists(imagesDir) then
    Directory.Delete(imagesDir, true)

Directory.CreateDirectory(imagesDir) |> ignore

let db = new SQLiteConnection(dbFile)

db.Execute("""
CREATE TABLE Categories(
    Id INTEGER PRIMARY KEY AUTOINCREMENT
);
""")

db.Execute("""
CREATE TABLE Abilities(
    Id INTEGER PRIMARY KEY AUTOINCREMENT
);
""")

db.Execute("""
CREATE TABLE Pokemons(
    Id INTEGER PRIMARY KEY,
    Weight REAL,
    Height REAL,
    Category INTEGER,
    Ability INTEGER,
    FOREIGN KEY(Category) REFERENCES Categories(Id),
    FOREIGN KEY(Ability) REFERENCES Abilities(Id)
);
""")

db.Execute("""
CREATE TABLE PokemonTypes(
    PokemonId INTEGER,
    Type INTEGER,
    FOREIGN KEY(PokemonId) REFERENCES Pokemons(Id)
);
""")

db.Execute("""
CREATE TABLE PokemonWeaknesses(
    PokemonId INTEGER,
    Type INTEGER,
    FOREIGN KEY(PokemonId) REFERENCES Pokemons(Id)
);
""")

db.Execute("""
CREATE TABLE CategoryTranslations(
    CategoryId INTEGER,
    Language TEXT,
    Name TEXT,
    FOREIGN KEY(CategoryId) REFERENCES Categories(Id)
);
""")

db.Execute("""
CREATE TABLE AbilityTranslations(
    AbilityId INTEGER,
    Language TEXT,
    Name TEXT,
    FOREIGN KEY(AbilityId) REFERENCES Abilities(Id)
);
""")

db.Execute("""
CREATE TABLE PokemonTranslations(
    PokemonId INTEGER,
    Language TEXT,
    Name TEXT,
    Description TEXT,
    FOREIGN KEY(PokemonId) REFERENCES Pokemons(Id)
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
            use file = File.Create(Path.Combine(imagesDir, filename))
            stream.CopyTo(file)
        with ex ->
            printfn $"Error downloading %s{link} : %s{filename}"
    }
    
task {
    let client = new HttpClient()
    for number = 1 to 151 do
        // Insert data into database
        let! response = client.GetAsync(pokemonUrl number)
        let! content = response.Content.ReadAsStringAsync()
        let data = System.Text.Json.JsonSerializer.Deserialize<PokemonData>(content)
        printfn $"Pokemon %d{number}: %s{data.name}"
        
        // Download images
        do! download client (largeImageLink data.name) (largeImageFileName number)
        do! download client data.sprites.front_default (mediumImageFileName number)
}