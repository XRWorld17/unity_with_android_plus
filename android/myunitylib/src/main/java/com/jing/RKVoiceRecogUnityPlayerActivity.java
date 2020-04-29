package com.jing;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.rokid.glass.instruct.InstructionManager;
import com.rokid.glass.instruct.entity.EntityKey;
import com.rokid.glass.instruct.entity.IInstructReceiver;
import com.rokid.glass.instruct.entity.InstructConfig;
import com.rokid.glass.instruct.entity.InstructEntity;
import com.unity3d.player.UnityPlayer;

public class RKVoiceRecogUnityPlayerActivity extends CustomActivity {

    private static final String TAG = "wwb-" + RKVoiceRecogUnityPlayerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i(TAG, "RKActivity onCreate");
    }


    @Override
    public InstructConfig configInstruct() {
        InstructConfig config = new InstructConfig();
        config.setActionKey(RKVoiceRecogUnityPlayerActivity.class.getName() + InstructConfig.ACTION_SUFFIX)
                .addInstructEntity(
                        new InstructEntity()
                                .addEntityKey(new EntityKey("上一个", null))
                                .addEntityKey(new EntityKey(EntityKey.Language.en, "last one"))
                                .setShowTips(true)
                                .setCallback(new IInstructReceiver() {
                                    @Override
                                    public void onInstructReceive(Activity act, String key, InstructEntity instruct) {
                                        doLast();
                                    }
                                })
                )
                .addInstructEntity(
                        new InstructEntity()
                                .addEntityKey(new EntityKey("下一个", null))
                                .addEntityKey(new EntityKey(EntityKey.Language.en, "next one"))
                                .setShowTips(true)
                                .setCallback(new IInstructReceiver() {
                                    @Override
                                    public void onInstructReceive(Activity act, String key, InstructEntity instruct) {
                                        doNext();
                                    }
                                })
                )
                .addInstructEntity(
                        new InstructEntity()
                                .addEntityKey(new EntityKey("进入视频", null))
                                .addEntityKey(new EntityKey(EntityKey.Language.en, "open video"))
                                .setShowTips(true)
                                .setIgnoreHelp(true)
                                .setCallback(new IInstructReceiver() {
                                    @Override
                                    public void onInstructReceive(Activity act, String key, InstructEntity instruct) {
                                        openVideo();
                                    }
                                })
                )
                .addInstructEntity(
                        new InstructEntity()
                                .addEntityKey(new EntityKey("测试", null))
                                .addEntityKey(new EntityKey(EntityKey.Language.en, "test this"))
                                .setShowTips(true)
                                .setIgnoreHelp(true)
                                .setCallback(new IInstructReceiver() {
                                    @Override
                                    public void onInstructReceive(Activity act, String key, InstructEntity instruct) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //openDialog();
                                            }
                                        });
                                    }
                                })
                );

        return config;
    }

    /**
     * 是否拦截处理当前语音指令，拦截后之前配置的指令闭包不会被调用
     * （可以用来提前处理一些指令，然后返回false）
     * @param command
     * @return true：拦截事件 false：不进行拦截
     */
    @Override
    public boolean doReceiveCommand(String command) {
        Log.d(TAG, "doReceiveCommand command = " + command);

        if ("测试".equals(command)) {
            return true;
        }
        return false;
    }


    private void doNext() {
        Log.d(TAG, "---java doNext---" );

        UnityPlayer.UnitySendMessage("Main Camera","unitydoNext","下一个");
    }

    private void doLast() {
        Log.d(TAG, "---java doLast---" );
        UnityPlayer.UnitySendMessage("Main Camera","unitydoLast","上一个");

    }

    private void openVideo() {
        Log.d(TAG, "---openVideo---" );
    }
}
