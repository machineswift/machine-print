package com.machine.elescale.util;

import java.io.IOException;
import java.io.InputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * Com21PollingListener类使用“轮训”的方法监听串口COM21，
 * 并通过COM21的输入流对象来获取该端口接收到的数据（在本文中数据来自串口COM11）。
 */
public class Com21PollingListener {
    public static void pollListener() {
		//1.定义变量
        CommPortIdentifier com = null;//未打卡的端口
        SerialPort serialCom = null;//打开的端口
        InputStream inputStream = null;//端口输入流

        try{
            //2.获取并打开串口COM1
            com = CommPortIdentifier.getPortIdentifier("COM1");
            serialCom = (SerialPort) com.open("COM1", 1000);
            
            //2.3.设置两个端口的参数
            try {
                serialCom.setSerialPortParams(
                        9600, //波特率
                        SerialPort.DATABITS_8,//数据位数
                        SerialPort.STOPBITS_1,//停止位
                        SerialPort.PARITY_NONE//奇偶位
                );
            } catch (UnsupportedCommOperationException e) {
                e.printStackTrace();
            }
            
            //3.获取串口的输入流对象
            inputStream = serialCom.getInputStream();

            //4.从串口读入数据
            //定义用于缓存读入数据的数组
            byte[] cache = new byte[1024];
            //记录已经到达串口COM21且未被读取的数据的字节（Byte）数。
            int availableBytes = 0;

            //无限循环，每隔20毫秒对串口COM21进行一次扫描，检查是否有数据到达
            while(true){
                //获取串口COM21收到的可用字节数
                availableBytes = inputStream.available();
                //如果可用字节数大于零则开始循环并获取数据
                while(availableBytes > 0){
                    //从串口的输入流对象中读入数据并将数据存放到缓存数组中
                    inputStream.read(cache);
                    //将获取到的数据进行转码并输出
                    for(int j = 0;j < cache.length && j < availableBytes; j++){
                        //因为COM11口发送的是使用byte数组表示的字符串，
                        //所以在此将接收到的每个字节的数据都强制装换为char对象即可，
                        //这是一个简单的编码转换，读者可以根据需要进行更加复杂的编码转换。
                        System.out.print((char)cache[j]);
                    }
                    System.out.println();
                    //更新循环条件
                    availableBytes = inputStream.available();
                }
                //让线程睡眠20毫秒
                Thread.sleep(20);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch (NoSuchPortException e) {
            //找不到串口的情况下抛出该异常
            e.printStackTrace();
        } catch (PortInUseException e) {
            //如果因为端口被占用而导致打开失败，则抛出该异常
            e.printStackTrace();
        } catch (IOException e) {
            //如果获取输出流失败，则抛出该异常
            e.printStackTrace();
        }
	}
}
