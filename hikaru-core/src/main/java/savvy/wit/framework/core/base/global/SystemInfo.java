package savvy.wit.framework.core.base.global;

import org.hyperic.sigar.*;
import savvy.wit.framework.core.base.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/***************************
 * Title: 获取当前服务器系统信息
 * File name: SystemInfo
 * Author: zhoujiajun
 * Date: 2019/12/27 17:42
 * Description:
 * 依赖sigar-amd64-winnt.dll
 * 将该文件部署到jdk安装bin目录下即可
 ***************************/
public class SystemInfo {


    private static Map<String, String> memory = new HashMap();
    private static Map<String, String> cpu = new HashMap();
    private static Map<String, String> os = new HashMap();
    private static Map<String, String> user = new HashMap();
    private static Map<String, String> file = new HashMap();
    private static Map<String, String> net = new HashMap();



    public static Map<String, String> memory() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        memory.put("Total", mem.getTotal() / 1024L + "K av");
        memory.put("Used", mem.getUsed() / 1024L + "K Used");
        memory.put("Free", mem.getFree() / 1024L + "K free");
        Swap swap = sigar.getSwap();
        // 交换区
        memory.put("swap Total", swap.getTotal() / 1024L + "K av");
        memory.put("swap Used", swap.getUsed() / 1024L + "K used");
        memory.put("swap Free", swap.getFree() / 1024L + "K free");
        return memory;
    }

    public static Map<String, String> cpu() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo[] cpuInfoList = sigar.getCpuInfoList();
        CpuPerc[] cpuPercs = null;
        cpuPercs = sigar.getCpuPercList();
        for (int i = 0; i < cpuInfoList.length; i++) {
            Map<String, Object> map = new HashMap<>();
            CpuInfo cpuInfo = cpuInfoList[i];
            map.put("Total MHz", cpuInfo.getMhz()); // Cpu 总量MHz
            map.put("Vendor", cpuInfo.getVendor()); // 厂商： Intel
            map.put("Model", cpuInfo.getModel());   // 类别： Core
            map.put("CacheSize", cpuInfo.getCacheSize()); // 缓冲存储器数量
            // Cpu 使用率: 总使用率 + 用户、系统、等待、错误、空闲
            map.put("perc", "Combined" + CpuPerc.format(cpuPercs[i].getCombined()) + " => " + cpuPercs[i]);
            cpu.put("The " + i + " Block", JsonUtil.toJson(map));
        }
        return cpu;
    }

    public static Map<String, String> os() {
        Sigar sigar = new Sigar();
        OperatingSystem OS = OperatingSystem.getInstance();
        // 系统内核类型
        os.put("OS", OS.getArch());
        os.put("CpuEndian", OS.getCpuEndian());
        os.put("DataModel", OS.getDataModel());
        // 系统描述
        os.put("description", OS.getDescription());
        // 操作系统类型
        os.put("Name", OS.getName());
        os.put("PatchLevel", OS.getPatchLevel());
        // 版本
        os.put("Version", OS.getVersion());
        return os;
    }

    public static Map<String, String> user() throws SigarException {
        Sigar sigar = new Sigar();
        Who who[] = sigar.getWhoList();
        if (who != null && who.length > 0) {
            for (int i = 0; i < who.length; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("User", who[i].getUser());
                map.put("Control", who[i].getDevice());
                map.put("Host", who[i].getHost());
                map.put("Time", who[i].getTime());
                user.put("The " + i + " user" , JsonUtil.toJson(map));
            }
        }
        return user;
    }

    public static Map<String, String> file() throws SigarException {
        Sigar sigar = new Sigar();
        FileSystem[] fileSystemList = sigar.getFileSystemList();
        for (int i = 0; i < fileSystemList.length; i++) {
            FileSystem fs = fileSystemList[i];
            Map<String, Object> map = new HashMap<>();
            map.put("Path", fs.getDirName());
            map.put("Flag", fs.getFlags());
            // 文件系统类型：如FAT32
            map.put("SystemType", fs.getSysTypeName());
            // 类型名：如本地硬盘、光驱
            map.put("TypeName", fs.getTypeName());
            map.put("Type", fs.getType());
            FileSystemUsage usage = null;
            switch (fs.getType()) {
                case 0: // TYPE_UNKNOWN 未知
                    break;
                case 1: // TYPE_NONE
                    break;
                case 2: // TYPE_LOCAL_DISK  本地硬盘
                    usage = sigar.getFileSystemUsage(fs.getDirName());
                    map.put("Total", usage.getTotal() + "KB");
                    map.put("Free", usage.getFree() + "KB");
                    map.put("Avail", usage.getAvail() + "KB");
                    map.put("Used", usage.getUsed() + "KB");
                    double usePercent = usage.getUsePercent();
                    // 资源利用率
                    map.put("Percent", usePercent + "%");
                    map.put("Reads", usage.getDiskReads());
                    map.put("Writes", usage.getDiskWrites());
                    break;
                case 3: // TYPE_NETWORK 网络
                    break;
                case 4: // TYPE_RAM_DISK 闪存
                    break;
                case 5: // TYPE_CDROM 光盘
                    break;
                case 6: // TYPE_SWAP 页面交换
                    break;
            }
            file.put("drive" + i, fs.getDevName());
            file.put("info" + i, JsonUtil.toJson(map));
        }
        return file;
    }

    public static Map<String, String> net() {
        Sigar sigar = new Sigar();
        // 略
        return net;
    }
}
