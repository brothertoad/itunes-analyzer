# iTunes Analyzer

This is a simple Java program that scans an iTunes music library and
creates an array listing all the songs it finds.  The output is a JSON
file.  The JSON file is not pretty printed.  If you wish to pretty print
it on a system that has Python installed (Mac/Linux), you can run

python -m json.tool < ugly_JSON > pretty_JSON

For each song, the title (property name TITLE), album (ALBUM), artist (ARTIST),
track number (TRACK), and total number of tracks (TRACK_TOTAL) are included.

A little background: I created this program because I was going to recreate my
Music library.  In particular, I was moving from a hackintosh to a Linux box,
plus I wanted to re-rip my CD's to the FLAC format.  I ran this program to get
a list of all the songs in my library.  I wrote a Sprint REST service that
would take the resulting JSON file and populate a Postgresql database.
The REST service also provided an API for a web page to show the songs,
so I could mark each one as I re-ripped it.

## Installation

This is a Maven project, so assuming you have Maven installed, you can
just clone the project to your machine and run 

mvn install

## Usage

Since I only ran this program a few times, I didn't bother to create a script
to run it.  You can run it from the project directory by typing in

java -cp target/itunes-analyzer-1.0-SNAPSHOT.jar iTunes-directory output-file

## License

Licensed under the MIT license.
