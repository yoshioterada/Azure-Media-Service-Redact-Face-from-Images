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
import com.microsoft.windowsazure.services.media.models.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Main {
    
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());
    private final static String INPUT_DIRECTORY;

    static {
        INPUT_DIRECTORY = PropertyReader.getPropertyValue("INPUT_DIRECTORY");
    }

    public static void main(String[] args) {
        try {
            Main main = new Main();
            main.executeMainService();
        } catch (InvalidKeyException | URISyntaxException | ServiceException | NoSuchAlgorithmException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void executeMainService() throws InvalidKeyException, MalformedURLException, URISyntaxException, ServiceException, FileNotFoundException, NoSuchAlgorithmException, IOException {
        RedactFaceService redactService = new RedactFaceService();
        //初期化
        redactService.init();

        // ファイルのアップロード
        executeFileUpload(redactService);

        //現在アップロードされているアセット一覧の取得
        ListResult<AssetInfo> beforeListAssets = redactService.listAssetID();
        //マスク処理
        executeRedatOperation(redactService, beforeListAssets);
        //ファイルのダウンロード
        ListResult<AssetInfo> afterListAssets = redactService.listAssetID();
        redactService.downLoadRedactedImageFile(afterListAssets);

        //アセットの削除       
        /*
        afterListAssets.forEach((AssetInfo asset) -> {
            try {
                redactService.deleteAsset(asset);
            } catch (ServiceException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });*/
        redactService.deleteAllAssets();

        //終了処理
        redactService.desroy();
    }

    private void executeFileUpload(RedactFaceService redactService) throws ServiceException, FileNotFoundException, NoSuchAlgorithmException, IOException {
        //ディレクトリ配下のファイル一覧を取得
        Stream<Path> files = Files.list(Paths.get(INPUT_DIRECTORY));
        files.forEach((Path path) -> {
            try {
                LOGGER.log(Level.FINE, "file name {0}", path.getFileName().toString());
                LOGGER.log(Level.FINE, "Abusolute Path name {0}", path.toAbsolutePath().toString());
                AssetInfo uploadFileAndCreateAsset = redactService.uploadFileAndCreateAsset(path.getFileName().toString(), path.toAbsolutePath().toString());
            } catch (ServiceException | FileNotFoundException | NoSuchAlgorithmException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    private void executeRedatOperation(RedactFaceService redactService, ListResult<AssetInfo> listAssetID) {
        // アップロードされているファイルを対象にマスク処理
        listAssetID.stream()
                .forEach((AssetInfo asset) -> {
                    try {
                        //画像へのマスク処理を開始
                        JobInfo job = redactService.redactFaceFromVideo(asset);
                        if (job != null) {
                            //マスク処理が完了まで待機
                            while (!redactService.checkFinishedOperationForRedact(job)) {
                                Thread.sleep(1000);
                            }
                            //編集後の動画を外部へ公開したい場合
                            //redactService.publishContents(job);
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, "FAILED to redact Face from Video : " + asset.getName());
                    }
                });
    }
}
