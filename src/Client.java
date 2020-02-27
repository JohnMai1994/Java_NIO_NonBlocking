import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {
    public static void main(String[] args) {
            try {
                // 创建SocketChannel
                // 绑定IP地址 & 端口号
                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.socket().connect(new InetSocketAddress("127.0.0.1", 8888));
                // 创建FileChannel
                // 绑定文件位置
                File file = new File("F:\\test.txt");
                FileChannel fileChannel = new FileInputStream(file).getChannel();

                ByteBuffer buffer = ByteBuffer.allocate(100);
                // 连接服务器成功
                socketChannel.read(buffer);
                buffer.flip();
                System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));
                buffer.clear();
                // 发送信息成功
                int num = 0;
                while ((num=fileChannel.read(buffer)) > 0) {
                    buffer.flip();
                    socketChannel.write(buffer);
                    buffer.clear();
                }
                System.out.println("文件已上传");
                if (num == -1) {
                    fileChannel.close();
                    socketChannel.shutdownOutput();
                }
                // 接受服务器
                socketChannel.read(buffer);
                buffer.flip();
                System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));
                buffer.clear();
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        };
    }
