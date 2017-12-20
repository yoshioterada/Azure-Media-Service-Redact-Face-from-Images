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
import com.yoshio3.json.ChannelValue;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import static org.hamcrest.core.IsEqual.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yoterada
 */
public class ChannelOperationTest {

    private final static Logger LOGGER = Logger.getLogger(ChannelOperationTest.class.getName());
    private ChannelOperation channelOperation;

    public ChannelOperationTest() {
    }

    @Before
    public void setUp() throws MalformedURLException, URISyntaxException, ServiceException {
        channelOperation = new ChannelOperation();
        channelOperation.init();
    }

    @After
    public void destroy() throws MalformedURLException, URISyntaxException, ServiceException {
        stopAllDummyChannel();
        deleteAllChannels();
    }

    /**
     * Test of isCompleted method, of class ChannelOperation.
     *
     * @throws java.util.concurrent.ExecutionException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testIsCompleted() throws ExecutionException, InterruptedException {
        System.out.println("testIsCompleted Start -----------------------------");
        try {
            //作成時の isCompleted() の検査
            String channelName = "testIsCompleted";
            Optional<String> createChannel = channelOperation.createChannel(channelName, "RTMP", channelOperation.createTemporalIPList(), channelOperation.createTemporalIPList());
            createChannel.ifPresent(opId -> {
                try {
                    final String suscceededChannelID = channelOperation.isCompleted(opId);
                    System.out.println("チャンネル作成完了");
                    //名前から channelID の取得
                    Optional<String> channelIDFromName = channelOperation.getChannelIDFromName(channelName);
                    channelIDFromName.ifPresent(channelid -> {
                        assertEquals(channelid, suscceededChannelID);
                    });
                } catch (InvalidChannelOperationException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            });
        } catch (InvalidChannelOperationException ex) {
            Logger.getLogger(ChannelOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //不正なチャネル ID が指定された場合
            String completed = channelOperation.isCompleted("hogehoge");
        } catch (InvalidChannelOperationException expected) {
            assertThat(expected.getMessage(), equalTo("isCompleted() failed :400 : Bad Request"));
        }
        System.out.println("testIsCompleted Done -----------------------------");
    }

    /**
     * Test of getChannelIDFromName method, of class ChannelOperation.
     */
    @Test
    public void testGetChannelIDFromName() {
        System.out.println("testGetChannelIDFromName Start -----------------------------");
        //存在していない　ID を指定した場合
        Optional<String> channelIDFromName1 = channelOperation.getChannelIDFromName("HOGEHOGE");
        assertFalse(channelIDFromName1.isPresent());
        System.out.println("testGetChannelIDFromName Done -----------------------------");
    }

    /**
     * Test of listChannel method, of class ChannelOperation.
     */
    @Test
    public void testListChannel() throws ExecutionException, InterruptedException {
        System.out.println("testListChannel Start -----------------------------");

        //現在のチャンネルを全停止
        stopAllDummyChannel();
        //現在のチャンネルを全削除
        deleteAllChannels();
        //チャンネルがクリアになった状態のリストサイズは 0
        List<ChannelValue> listChannel = channelOperation.listChannel();
        assertEquals(0, listChannel.size());
        //新規チャンネルの作成
        String newChannelName = "testListChannel";

        try {
            Optional<String> createChannel = channelOperation.createChannel(newChannelName, "RTMP", channelOperation.createTemporalIPList(), channelOperation.createTemporalIPList());
            createChannel.ifPresent(opId -> {
                try {
                    String suscceededChannelID = channelOperation.isCompleted(opId);
                    System.out.println("チャンネル作成完了");
                    List<ChannelValue> afterCreatedlistChannel = channelOperation.listChannel();
                    //新規作成後のリストの数が１ 
                    assertEquals(1, afterCreatedlistChannel.size());
                    afterCreatedlistChannel.stream().forEach(channelValue -> {
                        //作成時のチャンネル名と、作成後取得したチャンネル名が同一
                        assertEquals(newChannelName, channelValue.getName());
                    });

                } catch (InvalidChannelOperationException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            });
        } catch (InvalidChannelOperationException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        System.out.println("testListChannel Done -----------------------------");
    }

    /**
     * Test of startChannel method, of class ChannelOperation.
     */
    @Test
    public void testStartChannel() throws ExecutionException, InterruptedException {
        System.out.println("testStartChannel Start -----------------------------");
        try {
            //現在のチャンネルを全停止
            stopAllDummyChannel();
            //現在のチャンネルを全削除
            deleteAllChannels();
            //チャンネルの新規作成
            String newChannelName = "testStartChannel";
            createDummyChannel(newChannelName);
            //チャンネル名から ID を取得
            Optional<String> channelIDFromName = channelOperation.getChannelIDFromName(newChannelName);
            channelIDFromName.ifPresent(channelID -> {
                try {
                    //チャンネルの起動
                    Optional<String> startChannel = channelOperation.startChannel(channelID);
                    startChannel.ifPresent(startjobid -> {
                        try {
                            String completed = channelOperation.isCompleted(startjobid);
                            System.out.println("チャンネル起動完了");
                            //起動後の確認
                            List<ChannelValue> listChannel = channelOperation.listChannel();
                            Optional<ChannelValue> findedChannel = listChannel.stream()
                                    .filter((ChannelValue channelValue) -> channelValue.getId().equals(completed))
                                    .findFirst();
                            findedChannel.ifPresent(channel -> {
                                //正常系：起動したチャンネルのステータスが Running か否かの検証
                                assertEquals("Running", channel.getState());
                            });
                        } catch (InvalidChannelOperationException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });

                    try {
                        //異常系：既に起動済みのチャネルを再起動した場合
                        Optional<String> startChannel2 = channelOperation.startChannel(channelID);
                    } catch (InvalidChannelOperationException expected) {
                        assertThat(expected.getMessage(), equalTo("start channel failed :412 : Precondition Failed"));
                    }
                    stopAllDummyChannel();
                } catch (InvalidChannelOperationException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            });
            //異常系：不正な ID を指定した場合
            try {
                Optional<String> startChannel2 = channelOperation.startChannel("HOGEHOGE");
            } catch (InvalidChannelOperationException expected) {
                assertThat(expected.getMessage(), equalTo("start channel failed :400 : Bad Request"));
            }
        } catch (InvalidChannelOperationException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        System.out.println("testStartChannel Done -----------------------------");
    }

    /**
     * Test of stopChannel method, of class ChannelOperation.
     */
    @Test
    public void testStopChannel() {
        System.out.println("testStopChannel Start -----------------------------");
        try {
            //現在のチャンネルを全停止
            stopAllDummyChannel();
            //現在のチャンネルを全削除
            deleteAllChannels();
            //チャンネルの新規作成
            String newChannelName = "testStopChannel";
            createDummyChannel(newChannelName);
            //チャンネルの起動
            startDummyChannel(newChannelName);
            //チャンネル名から ID を取得
            Optional<String> channelIDFromName = channelOperation.getChannelIDFromName(newChannelName);
            channelIDFromName.ifPresent(channelID -> {
                try {
                    Optional<String> stopChannel = channelOperation.stopChannel(channelID);
                    stopChannel.ifPresent(stopJobid -> {
                        try {
                            String completed = channelOperation.isCompleted(stopJobid);
                            System.out.println("チャンネル・停止完了");

                            //停止後の確認
                            List<ChannelValue> listChannel = channelOperation.listChannel();
                            Optional<ChannelValue> findedChannel = listChannel.stream()
                                    .filter((ChannelValue channelValue) -> channelValue.getId().equals(completed))
                                    .findFirst();
                            findedChannel.ifPresent(channel -> {
                                //正常系：停止したチャンネルのステータスが Stoped か否かの検証
                                assertEquals("Stopped", channel.getState());
                            });
                        } catch (InvalidChannelOperationException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                        try {
                            //異常系：既に停止済みのチャネルを再起動した場合
                            Optional<String> startChannel2 = channelOperation.stopChannel(channelID);
                        } catch (InvalidChannelOperationException expected) {
                            assertThat(expected.getMessage(), equalTo("stop channel failed :412 : Precondition Failed"));
                        }
                    });
                    //異常系：不正な ID を指定した場合
                    try {
                        Optional<String> startChannel2 = channelOperation.stopChannel("HOGEHOGE");
                    } catch (InvalidChannelOperationException expected) {
                        assertThat(expected.getMessage(), equalTo("stop channel failed :400 : Bad Request"));
                    }
                } catch (InvalidChannelOperationException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            });
        } catch (ExecutionException | InterruptedException | InvalidChannelOperationException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        System.out.println("testStopChannel Done -----------------------------");
    }

    /**
     * Test of createChannel method, of class ChannelOperation.
     */
    @Test
    public void testCreateChannel() {
        System.out.println("testCreateChannel Start -----------------------------");
        try {
            //現在のチャンネルを全停止
            stopAllDummyChannel();
            //現在のチャンネルを全削除
            deleteAllChannels();

            String channelName = "testCreateChannel";
            Optional<String> channelIDFromName = channelOperation.getChannelIDFromName(channelName);
            Optional<String> createChannel = channelOperation.createChannel(channelName, "RTMP", channelOperation.createTemporalIPList(), channelOperation.createTemporalIPList());
            createChannel.ifPresent(opId -> {
                try {
                    System.out.println(opId);
                    String suscceededChannelID = channelOperation.isCompleted(opId);
                    System.out.println("チャンネル作成完了");

                    Optional<String> afterCreatedChannelIDFromName = channelOperation.getChannelIDFromName(channelName);
                    //作成完了時のチャンネルIDの値と、名前から検索したチャンネルIDが同一か否か
                    afterCreatedChannelIDFromName.ifPresent(id -> {
                        assertEquals(suscceededChannelID, id);
                    });
                } catch (InvalidChannelOperationException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            });
            //正常系：新規作成でリストのサイズが１
            List<ChannelValue> listChannel = channelOperation.listChannel();
            assertEquals(1, listChannel.size());

            try {
                //異常系：同一名でチャンネルを作成
                Optional<String> duplicatedChannel = channelOperation.createChannel(channelName, "RTMP", channelOperation.createTemporalIPList(), channelOperation.createTemporalIPList());
            } catch (InvalidChannelOperationException expected) {
//                System.out.println("異常系：同一名でチャンネルを作成:" + expected.getMessage());
                assertThat(expected.getMessage(), equalTo("create channel failed :409 : Conflict"));
            }

            try {
                //異常系：不正な名前でチャンネル作成
                String invalidName = "dddd:aaaa-hogehoge,1234*%";
                Optional<String> duplicatedChannel = channelOperation.createChannel(invalidName, "RTMP", channelOperation.createTemporalIPList(), channelOperation.createTemporalIPList());
            } catch (InvalidChannelOperationException expected) {
//                System.out.println("異常系：不正な名前でチャンネル作成:" + expected.getMessage());
                assertThat(expected.getMessage(), equalTo("create channel failed :400 : Bad Request"));
            }

            IntStream.range(0, 30).forEach((int intValue) -> {
                try {
                    //異常系：上限を超えたチャンネル作成
                    String invalidName = "testCreateChannel-" + intValue;
                    Optional<String> duplicatedChannel = channelOperation.createChannel(invalidName, "RTMP", channelOperation.createTemporalIPList(), channelOperation.createTemporalIPList());
                } catch (InvalidChannelOperationException inex) {
                    System.out.println("異常系：上限を超えたチャンネル作成:" + inex.getMessage());
                    //異常系：上限を超えたチャンネル作成:create channel failed :409 : Conflict
                    //異常系：上限を超えたチャンネル作成:create channel failed :400 : Bad Request
                } catch (ExecutionException | InterruptedException ex) {
                    Logger.getLogger(ChannelOperationTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (ExecutionException | InterruptedException | InvalidChannelOperationException ex) {
            Logger.getLogger(ChannelOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("testCreateChannel Done -----------------------------");
    }

    /**
     * Test of deleteChannel method, of class ChannelOperation.
     */
    @Test
    public void testDeleteChannel() throws ExecutionException, InterruptedException {
        System.out.println("testDeleteChannel Start -----------------------------");
        try {
            String channelName = "testDeleteChannel";
            createDummyChannel(channelName);

            deleteAllChannels();
            List<ChannelValue> listChannel = channelOperation.listChannel();
            assertEquals(0, listChannel.size());
        } catch (InvalidChannelOperationException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        System.out.println("testDeleteChannel Done -----------------------------");
    }

    /**
     * Test of resetChannel method, of class ChannelOperation.
     */
    @Test
    public void testResetChannel() {
        /*
        プログラムが停止してないとエラーが返ってくる。
        
        System.out.println("resetChannel");
        TODO
        何を見ればよい？
         */
    }

    private void deleteAllChannels() {
        //現在のチャンネル・リスト一覧を取得
        List<ChannelValue> clearListChannel = channelOperation.listChannel();
        //現在のチャンネルを全削除
        clearListChannel.stream().forEach(channelValue -> {
            try {
                Optional<String> deleteChannel = channelOperation.deleteChannel(channelValue.getId());
                deleteChannel.ifPresent(deleteJobid -> {
                    try {
                        String completed = channelOperation.isCompleted(deleteJobid);
                    } catch (InvalidChannelOperationException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    System.out.println("チャンネル・削除完了");
                });
            } catch (InvalidChannelOperationException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    private void createDummyChannel(String newChannelName) throws ExecutionException, InterruptedException, InvalidChannelOperationException {
        //新規チャンネルの作成
        Optional<String> createChannel = channelOperation.createChannel(newChannelName, "RTMP", channelOperation.createTemporalIPList(), channelOperation.createTemporalIPList());
        createChannel.ifPresent(opId -> {
            try {
                String suscceededChannelID = channelOperation.isCompleted(opId);
                System.out.println("チャンネル作成完了");
            } catch (InvalidChannelOperationException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    private void startDummyChannel(String channelName) {
        Optional<String> channelIDFromName = channelOperation.getChannelIDFromName(channelName);
        channelIDFromName.ifPresent(id -> {
            try {
                Optional<String> startChannel = channelOperation.startChannel(id);
                startChannel.ifPresent(startjobid -> {
                    try {
                        String completed = channelOperation.isCompleted(startjobid);
                        System.out.println("チャンネル起動完了");
                    } catch (InvalidChannelOperationException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
            } catch (InvalidChannelOperationException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    private void stopAllDummyChannel() {
        //チャンネルの一覧取得
        List<ChannelValue> listChannel = channelOperation.listChannel();
        listChannel.stream()
                .filter(channelValue -> !channelValue.getState().equals("Stopped"))
                .forEach(channelValue -> {
                    Optional<String> channelIDFromName = channelOperation.getChannelIDFromName(channelValue.getName());
                    channelIDFromName.ifPresent(id -> {
                        try {
                            Optional<String> stopChannel = channelOperation.stopChannel(id);
                            stopChannel.ifPresent(stopJobid -> {
                                try {
                                    String completed = channelOperation.isCompleted(stopJobid);
                                    System.out.println("チャンネル・停止完了");
                                } catch (InvalidChannelOperationException ex) {
                                    LOGGER.log(Level.SEVERE, null, ex);
                                }
                            });
                        } catch (InvalidChannelOperationException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                });
    }
}
