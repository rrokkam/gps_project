Controls for the map are standard: scroll for zoom, drag for pan. Left
clicking on the map sets a start location, right clicking an end location.
Clicking "Get Directions" on the left side will compute a route if possible.
Clicking "Toggle Drive There" will toggle drive there mode.

State information, including the distances between nodes in the computed
directions, are printed to the console.

Off course indications are not functioning. The debug printouts make it seem
like the directions are being computed over and over again - I haven't figured
this out yet. If I make another commit after the deadline, it will probably
be to fix this.

For now, the off course indications are disabled. They can be enabled in their
current state by uncommenting line 72 in the MapApplication class.
