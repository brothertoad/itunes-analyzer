package net.brothertoad.home.music;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

import org.jaudiotagger.audio.*;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import com.google.gson.*;

/**
 * Hello world!
 *
 */
public class ITunesAnalyzer extends SimpleFileVisitor<Path>
{
    final List<FieldKey> keyList = Arrays.asList(FieldKey.ALBUM, FieldKey.ARTIST, FieldKey.TITLE,
            FieldKey.TRACK, FieldKey.TRACK_TOTAL);
    List<Map<String,String>> songList = new ArrayList<>();

    public static void main( String[] args ) throws Exception {
        if (args.length < 2) {
            System.err.println("Need a directory and an output file.");
            System.exit(0);
        }
        ITunesAnalyzer analyzer = new ITunesAnalyzer();
        Path top = Paths.get(args[0]);
        Files.walkFileTree(top, analyzer);
        analyzer.writeSongList(args[1]);
    }

    private void writeSongList(String path) throws Exception {
        // Need to write songList as JSON.
        Gson gson = new Gson();
        String json = gson.toJson(songList);
        FileOutputStream fos = new FileOutputStream(path);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        osw.write(json);
        osw.close();
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException
    {
        try {
            AudioFile audioFile = AudioFileIO.read(path.toFile());
            Tag tag = audioFile.getTag();
            Map<String,String> map = new HashMap<>();
            for (FieldKey key: keyList) {
                String first = tag.getFirst(key);
                if (first != null && !first.isEmpty()) {
                    // System.out.println("path is " + path + ", key is " + key + ", value is " + first);
                    map.put(key.toString(), first);
                }
            }
            if (!map.isEmpty()) {
                songList.add(map);
            }
        }
        catch(Exception e) {

        }
        return FileVisitResult.CONTINUE;
    }

}
