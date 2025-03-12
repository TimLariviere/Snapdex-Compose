import 'package:flutter/material.dart';

class PokemonDetailsScreen extends StatefulWidget {
  final int pokemonId;

  const PokemonDetailsScreen({Key? key, required this.pokemonId}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _PokemonDetailsScreenState();
}

class _PokemonDetailsScreenState extends State<PokemonDetailsScreen> {
  late int pokemonId;

  @override
  void initState() {
    super.initState();
    pokemonId = widget.pokemonId;
  }

  @override
  Widget build(BuildContext context) {
    return
      Padding(
        padding: EdgeInsets.symmetric(horizontal: 16, vertical: 24),
        child: Column(
          spacing: 24,
          children: [
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "Charizard",
                  style: Theme.of(context).textTheme.displayLarge,
                ),
                Text(
                  "Nº${pokemonId.toString().padLeft(4, '0')}",
                  style: Theme.of(context).textTheme.titleSmall,
                )
              ],
            )
          ]
        )
      );
  }
}