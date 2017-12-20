/**
 *
 * @author Yoshio Terada
 *
 *         Copyright (c) 2017 Yoshio Terada
 *
 *         Permission is hereby granted, free of charge, to any person obtaining
 *         a copy of this software and associated documentation files (the
 *         "Software"), to deal in the Software without restriction, including
 *         without limitation the rights to use, copy, modify, merge, publish,
 *         distribute, sublicense, and/or sell copies of the Software, and to
 *         permit persons to whom the Software is furnished to do so, subject to
 *         the following conditions:
 *
 *         The above copyright notice and this permission notice shall be
 *         included in all copies or substantial portions of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *         EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *         MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *         NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *         BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *         ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *         CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *         SOFTWARE.
 */
package com.yoshio3;

import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.media.models.AssetInfo;
import com.microsoft.windowsazure.services.media.models.ListResult;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author yoterada
 */
public class RedactFaceServiceTest {

    private final static String INPUT_DIRECTORY;

    static {
        INPUT_DIRECTORY = PropertyReader.getPropertyValue("INPUT_DIRECTORY");
    }

    RedactFaceService instance;

    public RedactFaceServiceTest() {
    }

    /**
     * Test of init method, of class RedactFaceService.
     * @throws java.lang.Exception
     */
    @Before
    public  void testInit() throws Exception {
        System.out.println("init");
        instance = new RedactFaceService();
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of desroy method, of class RedactFaceService.
     */
    @After
    public  void testDesroy() {
        System.out.println("desroy");
        instance.desroy();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of listAssetID method, of class RedactFaceService.
     * @throws java.lang.Exception
     */
    @Test
    public void testListAssetID() throws Exception {
        System.out.println("listAssetID");
        String assetName = "cognitive.mp4";

        //初期状態削除 = サイズ 0
        instance.deleteAllAssets();
        ListResult<AssetInfo> result = instance.listAssetID();
        assertEquals(0, result.size());
        //アセットの作成 = サイズ 1
        AssetInfo uploadFileAndCreateAsset = instance.uploadFileAndCreateAsset(assetName, "/tmp/Media/in/cognitive.mp4");
        ListResult<AssetInfo> result2 = instance.listAssetID();
        assertEquals(1, result2.size());
        //
        assertEquals(assetName, uploadFileAndCreateAsset.getName());
    }

    /**
     * Test of deleteAsset method, of class RedactFaceService.
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteAsset() throws Exception {
        System.out.println("deleteAsset");
        String assetName = "cognitive.mp4";
        instance.deleteAllAssets();

        ListResult<AssetInfo> before = instance.listAssetID();
        assertEquals(0, before.size());
        AssetInfo uploadFileAndCreateAsset = instance.uploadFileAndCreateAsset(assetName, "/tmp/Media/in/cognitive.mp4");
        ListResult<AssetInfo> created = instance.listAssetID();
        assertEquals(1, created.size());
        instance.deleteAsset(uploadFileAndCreateAsset);
        // TODO review the generated test code and remove the default call to fail.
        ListResult<AssetInfo> after = instance.listAssetID();
        assertEquals(0, after.size());
    }

    /**
     * Test of deleteAllAssets method, of class RedactFaceService.
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteAllAssets() throws Exception {
        System.out.println("deleteAllAssets");
        String assetName = "cognitive.mp4";

        instance.deleteAllAssets();        
        ListResult<AssetInfo> before = instance.listAssetID();
        assertEquals(0, before.size());
        
        IntStream.range(0, 5).forEach(intValue -> {
            try {
                AssetInfo uploadFileAndCreateAsset = instance.uploadFileAndCreateAsset(intValue + assetName, "/tmp/Media/in/cognitive.mp4");
            } catch (ServiceException | FileNotFoundException | NoSuchAlgorithmException ex) {
                Logger.getLogger(RedactFaceServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ListResult<AssetInfo> created = instance.listAssetID();
        assertEquals(5, created.size());
        
        instance.deleteAllAssets();

        ListResult<AssetInfo> after = instance.listAssetID();
        assertEquals(0, before.size());
    }

    /**
     * Test of redactFaceFromVideo method, of class RedactFaceService.
     * @throws java.lang.Exception
     */
    @Test
    public void testRedactFaceFromVideo() throws Exception {
        /*
        System.out.println("redactFaceFromVideo");
        AssetInfo assetInfo = null;
        RedactFaceService instance = new RedactFaceService();
        JobInfo expResult = null;
        JobInfo result = instance.redactFaceFromVideo(assetInfo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }


    /**
     * Test of checkFinishedOperationForRedact method, of class
     * RedactFaceService.
     * @throws java.lang.Exception
     */
    @Test
    public void testCheckFinishedOperationForRedact() throws Exception {
        /*
        System.out.println("checkFinishedOperationForRedact");
        JobInfo job = null;
        RedactFaceService instance = new RedactFaceService();
        boolean expResult = false;
        boolean result = instance.checkFinishedOperationForRedact(job);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of uploadFileAndCreateAsset method, of class RedactFaceService.
     * @throws java.lang.Exception
     */
    @Test
    public void testUploadFileAndCreateAsset() throws Exception {
        /*
        System.out.println("uploadFileAndCreateAsset");
        String assetName = "";
        String fileName = "";
        RedactFaceService instance = new RedactFaceService();
        AssetInfo expResult = null;
        AssetInfo result = instance.uploadFileAndCreateAsset(assetName, fileName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of downLoadRedactedImageFile method, of class RedactFaceService.
     * @throws java.lang.Exception
     */
    @Test
    public void testDownLoadRedactedImageFile() throws Exception {
        /*
        System.out.println("downLoadRedactedImageFile");
        ListResult<AssetInfo> assets = null;
        RedactFaceService instance = new RedactFaceService();
        instance.downLoadRedactedImageFile(assets);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

}
