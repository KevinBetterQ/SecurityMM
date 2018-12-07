
# SecurityMM 实现细节

## 图形用户界面实现
- 整个界面布局采用BorderLayout布局，左右两侧分别为输入文本区与输出文本区，利用JScrollPane与JTextArea实现，中间为加解密、数字签名方式的JComboBox<String>下拉列表组件与一些按钮，还有密钥的JTextField输入区。

## 加解密实现
- Base64：Base64其实是一种编码方式，所以实现起来较为简单，利用JDK本身带有的BASE64Encoder与BASE64Decoder类方法实现。  
- DES：是一种使用密钥加密的块算法，本次设计利用JDK实现，利用输入的密钥生成并转换Key；Cipher类的实例化（getInstance）、初始化（init选择模式与Key）；最后执行（doFinal）加解密  
- AES：速度快，安全级别高，基于排列与置换算法，但本次实现依然是调用的第三方的类库。首先生成Key，然后利用Cipher进行加解密。  
- PBE：是一种基于口令的加密算法，其特点是使用口令代替了密钥，而且给口令加了点“盐”，这个盐和口令组合使用进行加解密。实现起来首先初始化“盐”并生成口令，依然利用Cipher类进行加解密。

## 数字签名实现
- MD5：消息摘要算法，主要用于验证数据完整性。利用Java开发工具包中的MessageDigest类实现。
- SHA：安全哈希算法（Secure Hash Algorithm）主要适用于数字签名标准里面定义的数字签名算法。对于长度小于2^64位的消息，SHA1会产生一个160位的消息摘要。依然利用Java开发工具包中的MessageDigest类实现。
- MAC：消息认证码，含有密钥的散列函数算法，可以采用org.bouncycastle.crypto.macs.HMac第三方类实现，使用Hmac类实例化、初始化、执行。