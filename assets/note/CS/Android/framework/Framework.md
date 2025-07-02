AOSP的编译一般是在[[Ubuntu]]上进行。


### 更改下载源
官方源换中科大源，前方要看sources.list是什么源，这个本质上是替换字段
```
sudo sed -i 's/cn.archive.ubuntu.com/mirrors.ustc.edu.cn/g' /etc/apt/sources.list
```

### Vmware Tool
```
sudo apt-get update  
sudo apt-get install open-vm-tools-desktop  
sudo apt-get install open-vm-tools
```
### 下载必要环境

```
sudo apt-get install git-core gnupg flex bison build-essential zip curl zlib1g-dev gcc-multilib g++-multilib libc6-dev-i386 libncurses5 lib32ncurses5-dev x11proto-core-dev libx11-dev lib32z1-dev libgl1-mesa-dev libxml2-utils xsltproc unzip fontconfig python
```

### 下载repo工具
```
mkdir ~/bin
curl https://storage.googleapis.com/git-repo-downloads/repo > ~/bin/repo
chmod a+x ~/bin/repo
```
repo 的运行过程中会尝试访问官方的 git 源更新自己，可以将如下内容复制到你的 ~/.bashrc 或者 ~/.zshrc 里。

```
export REPO_URL='https://gerrit-googlesource.proxy.ustclug.org/git-repo'
PATH=~/bin:$PATH
source ~/.bashrc
```

### 初始化仓库并同步远程代码
可以先repo init查看repo是否要更新，顺便验证一下换的repo源有没有用。

```
git config --global user.email "xianjie.zhang516@gmail.com"
git config --global user.name "xinajie"
mkdir aosp 
cd aosp
#初始化仓库,-b 指示分支，这里使用 android12
repo init -u git://mirrors.ustc.edu.cn/aosp/platform/manifest -b android-12.1.0_r27
repo init -u https://mirrors.ustc.edu.cn/aosp/platform/manifest -b android-12.1.0_r27
#同步远程代码
repo sync
```

## 编译

```sh
source build/envsetup.sh
如果是 Android13
lunch sdk_phone_x86_64
lunch aosp_x86_64-eng
make clean
make -j16
运行模拟器
emulator -verbose -cores 4 -show-kernel
```

### 编译SDK
```sh
source build/envsetup.sh
lunch sdk-eng
# or
#lunch sdk-userdebug
# or
#lunch sdk-user
make sdk
```
### 运行指定模块
```sh
aidegen Settings -i s
```

删除编译产物
```bash
make clobber 
```

