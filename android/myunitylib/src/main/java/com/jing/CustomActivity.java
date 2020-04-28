package com.jing;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.rokid.glass.instruct.InstructionManager;
import com.rokid.glass.instruct.Integrate.IInstruction;
import com.rokid.glass.instruct.VoiceInstruction;


import com.rokid.glass.instruct.entity.InstructConfig;
import com.unity3d.player.UnityPlayerActivity;


public  abstract  class CustomActivity extends UnityPlayerActivity implements IInstruction {

    protected InstructionManager mInstructionManager;
    protected InstructionManager.IInstructionListener mIInstructionListener = new InstructionManager.IInstructionListener() {
        @Override
        public boolean onReceiveCommand(String command) {
            return doReceiveCommand(command);
        }
    };

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i("unity_with_android_plus", "老子就是自定义的Activity");

        //VoiceInstruction.init(this);

        mInstructionManager = new InstructionManager(this, closeInstruction(),configInstruct(),mIInstructionListener);
    }

    protected void onStart() {
        super.onStart();
        if(mInstructionManager != null) {
            mInstructionManager.onStart();
        }
    }

    @Override
    protected void onResume() {
        if (mInstructionManager != null) {
            mInstructionManager.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mInstructionManager != null) {
            mInstructionManager.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mInstructionManager != null) {
            mInstructionManager.onDestroy();
            mInstructionManager = null;
        }
        super.onDestroy();
    }

    /**
     * 清空当前指令组
     */
    @Override
    public void clearWtWords() {
        if (mInstructionManager != null) {
            mInstructionManager.clearWtWords();
        }
    }

    /**
     * 是否关闭语音指令开关， 默认开启，继承可以选择关闭
     *
     * @return false:开启， true:关闭
     */
    @Override
    public boolean closeInstruction() {
        return false;
    }

    /**
     * 设置当前识别到的指令
     *
     * @param showing
     */
    public void setMenuShowing(boolean showing) {
        if (mInstructionManager != null) {
            mInstructionManager.setMenuShowing(showing);
        }
    }

    /**
     * 插件浮层相关UI已经准备并添加到主View树完毕，可以进行UI相关修改
     */
    @Override
    public void onInstrucUiReady() {

    }

    /**
     * 插件帮助UI已经准备并添加到主View树完毕，可以进行UI相关修改
     */
    @Override
    public void onInstrucHelpReady() {

    }

    @Override
    public boolean isHelpLayerShowing() {
        if (mInstructionManager != null) {
            return mInstructionManager.isHelpLayerShowing();
        }

        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean ret = false;
        if (mInstructionManager != null) {
            ret = mInstructionManager.onKeyDown(keyCode, event);
        }
        return ret || super.onKeyDown(keyCode, event);
    }


    /**
     * 获取语音指令配置
     *
     * @return
     */
    @Override
    public abstract InstructConfig configInstruct();

    /**
     * 是否拦截处理当前语音指令，拦截后之前配置的指令闭包不会被调用
     * （可以用来提前处理一些指令，然后返回false）
     * @param command
     * @return true：拦截事件 false：不进行拦截
     */
    @Override
    public abstract boolean doReceiveCommand(String command);


}
