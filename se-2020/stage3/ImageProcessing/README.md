# ImageProcessing

18342138 郑卓民

项目结构：

- goal: 存储给定的标准处理之后的图片
- ImageReader：库
- result：存储处理之后保存的图片
- src：
  - ImageProcessingTest.java： 测试文件
  - ImageReaderRunner.java： 运行类
  - ImplementImageIO.java： myImageIO
  - ImplementImageProcessor.java： myProcessor  
- target：存储待处理的图片


运行：

进入到src文件夹执行：

运行runner：

`javac -classpath .:../ImageReader/ImageReader.jar ImageReaderRunner.java`

`java -classpath .:../ImageReader/ImageReader.jar ImageReaderRunner`

运行test：

`javac -classpath .:../ImageReader/ImageReader.jar:junit-4.9.jar ImageProcessTest.java`

`java -classpath .:../ImageReader/ImageReader.jar:junit-4.9.jar org.junit.runner.JUnitCore ImageProcessTest`
