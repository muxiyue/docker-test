package file.monitor;

import com.google.common.base.Charsets;
import com.sun.nio.file.SensitivityWatchEventModifier;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import static com.sun.jmx.mbeanserver.Util.cast;

/**
 *
 3.1 java.nio.file.WatchService接口
 监听服务， 方法有：

 WatchKey take()：等待下一次的监听结果，没有则等待;
 WatchKey poll()：获取监听结果，没有则返回null;
 WatchKey poll(long timeout, TimeUnit unit)：获取监听结果，最多等待指定时间，没有则返回null;
 void close()：关闭监听服务;

 监听服务可通过下面方式获取：

 WatchService watcher = FileSystems.getDefault().newWatchService();

 3.2 java.nio.file.WatchEvent接口
 监听事件， 方法有：

 Kind<T> kind()：监听事件的类型；
 int count()：事件的个数，大于1表示是一个重复事件；
 T context()：事件的上下文；

 事件类型的管理位于java.nio.file.StandardWatchEventKinds类下，使用内部静态类StdWatchEventKind(实现WatchEvent.Kind接口)表示，具体类别有：

 StandardWatchEventKinds.OVERFLOW：事件丢失或失去
 StandardWatchEventKinds.ENTRY_CREATE：目录内实体创建或本目录重命名
 StandardWatchEventKinds.ENTRY_MODIFY：目录内实体修改
 StandardWatchEventKinds.ENTRY_DELETE：目录内实体删除或重命名

 3.3 java.nio.file.WatchKey接口
 监听key,， 方法有：

 boolean isValid()：监听key是否合法,true:合法，false:不合法；
 List<WatchEvent<?>> pollEvents()：拉取并删除位于这个监听key的监听事件列表(可能为空)；
 boolean reset()：监听key复位，复位后，新的pending事件才会再次进入监听,返回true:key合法且复位成功；
 void cancel()：取消key的注册；
 Watchable watchable()：返回该key关联的Watchable；

 3.4 java.nio.file.Watchable 接口
 监听注册， 方法有：

 WatchKey register(WatchService watcher, WatchEvent.Kind<?>... events)：监听注册，包含监听服务和监听事件；
 WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers)：监听注册，包含监听服务、监听事件、监听修改(如继承自WatchEvent.Modifier接口的com.sun.nio.file.SensitivityWatchEventModifier，可控制监听频率，默认就10秒， 类似还有com.sun.nio.file.ExtendedWatchEventModifier)

 3.5 其他方法和类
 FileSystem java.nio.file.FileSystems.getDefault()：获取本机文件系统；
 WatchService java.nio.file.FileSystem.newWatchService()：构建新的监听服务；
 Path java.nio.file.Paths.get(String first, String... more)：将文件路径转为Path对象；
 WatchKey java.nio.file.Path.register(WatchService watcher,WatchEvent.Kind<?>... events):注册监听；


 */



public class WatchServicePaper {
    public static void main(String[] args) throws Exception {
        //监听目录(项目resource/config目录)，即运行时classes/config目录
        String baseDir = Thread.currentThread().getContextClassLoader().getResource("config").getPath();
        //监听文件
        String target_file = "t.txt";
        //构造监听服务
        WatchService watcher = FileSystems.getDefault().newWatchService();
        //监听注册，监听实体的创建、修改、删除事件，并以高频率(每隔2秒一次，默认是10秒)监听
        Paths.get(baseDir).register(watcher,
                new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_MODIFY,
                        StandardWatchEventKinds.ENTRY_DELETE},
                SensitivityWatchEventModifier.HIGH);
        while (true) {
            //每隔3秒拉取监听key
            WatchKey key = watcher.poll(3, TimeUnit.SECONDS);  //等待，超时就返回
            //监听key为null,则跳过
            if (key == null) {
                continue;
            }
            //获取监听事件
            for (WatchEvent<?> event : key.pollEvents()) {
                //获取监听事件类型
                WatchEvent.Kind kind = event.kind();
                //异常事件跳过
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }
                //获取监听Path
                Path path = cast(event.context());
                //只关注目标文件
                if (!target_file.equals(path.toString())) {
                    continue;
                }
                //文件删除
                if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.printf("file delete, type:%s  path:%s \n", kind.name(), path);
                    continue;
                }
                //构造完整路径
                Path fullPath = Paths.get(baseDir, path.toString());
                //获取文件
//                File f = fullPath.toFile();
                //读取文件内容
                String content = new String(Files.readAllBytes(fullPath), Charsets.UTF_8);
                //按行读取文件内容
//                List<String> lineList = Files.readAllLines(fullPath);
                //输出事件类型、文件路径及内容
                System.out.printf("type:%s  path:%s  content:%s\n", kind.name(), path, content);
            }
            //处理监听key后(即处理监听事件后)，监听key需要复位，便于下次监听
            key.reset();
        }
    }
}
