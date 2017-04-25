package markmann.dennis.fileExtractor.tests.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import markmann.dennis.fileExtractor.logic.FileRenamer;
import markmann.dennis.fileExtractor.mediaObjects.Anime;

public class FileRenamerTest {

    @Test
    public void testHandleAnimeRenaming() {
        String originalFineName = "[HorribleSubs] Kono Subarashii Sekai ni Shukufuku wo! 2 - 07 [1080p].mkv";
        String resultTitle = "Kono Subarashii Sekai ni Shukufuku wo! 2";
        String resultEpisode = "07";
        String resultExtension = "mkv";
        String resultTitleComplete = "Kono Subarashii Sekai ni Shukufuku wo! 2 - 07.mkv";
        String resultTitleCompleteNoExt = "Kono Subarashii Sekai ni Shukufuku wo! 2 - 07";

        Anime anime = new FileRenamer().handleAnimeRenaming(originalFineName, new Anime(), false);
        assertEquals(resultTitle, anime.getTitle());
        assertEquals(resultEpisode, anime.getEpisode());
        assertEquals(resultExtension, anime.getExtension());
        assertEquals(resultTitleComplete, anime.getCompleteTitle());
        assertEquals(resultTitleCompleteNoExt, anime.getCompleteTitleNoExt());
    }

}
