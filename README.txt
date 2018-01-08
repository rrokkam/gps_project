README:

This is the map project of Rohith Rokkam - CSE260 Fall 2016.

HANDIN 4:
UML diagrams are included in the PRJ4 folder. There is no overall
class diagram, since it would be far too messy. Separate class diagrams
for different portions of this program are included instead. A new 
diagram was included for the Coordinates framework.

Controls for the map are standard: scroll for zoom, drag for pan. Left
clicking on the map sets a start location, right clicking an end location.
Clicking "Get Directions" on the left side will compute a route if possible.
Clicking "Toggle Drive There" will toggle drive there mode.

Note that the MapGenerator.java file is compatible with usb.osm but not
rhode_island.osm. I make no guarantees as to whether these files will load.

State information, including the distances between nodes in the computed
directions, are printed to the console.

Off course indications are not functioning. The debug printouts make it seem
like the directions are being computed over and over again - I haven't figured
this out yet. If I make another commit after the deadline, it will probably
be to fix this.

For now, the off course indications are disabled. They can be enabled in their
current state by uncommenting the line 72 in the MapApplication class.

Thanks for this project. It was entertaining and taught me a lot about good
software design. 