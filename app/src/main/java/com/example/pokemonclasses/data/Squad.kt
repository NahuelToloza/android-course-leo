package com.example.pokemonclasses.data

/*
{
  "squadName": "Super hero squad",
  "homeTown": "Metro City",
  "formed": 2016,
  "secretBase": "Super tower",
  "active": true,
  "members": [
    {
      "name": "Molecule Man",
      "age": 29,
      "secretIdentity": "Dan Jukes",
      "powers": ["Radiation resistance", "Turning tiny", "Radiation blast"]
    },
    {
      "name": "Madame Uppercut",
      "age": 39,
      "secretIdentity": "Jane Wilson",
      "powers": [
        "Million tonne punch",
        "Damage resistance",
        "Superhuman reflexes"
      ]
    },
    {
      "name": "Eternal Flame",
      "age": 1000000,
      "secretIdentity": "Unknown",
      "powers": [
        "Immortality",
        "Heat Immunity",
        "Inferno",
        "Teleportation",
        "Interdimensional travel"
      ]
    }
  ]
}
 */
data class Squad(
    //@SerializedName("squadName")
    val name: String,
    val formedName: Int,
    val members: List<Member>
) {
    companion object {
        fun createHardcodeSquad() = Squad(
            "Super hero squad",
            2016,
            listOf(
                Member(
                    "Molecule Man",
                    29,
                    "Dan Jukes",
                    listOf("Radiation resistance", "Turning tiny", "Radiation blast")
                )
            )
        )
    }
}

data class Member(
    val name: String,
    val age: Long,
    val secretIdentity: String,
    val powers: List<String>
)