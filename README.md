# Dağıtık Abonelik Sistemi (Distributed Subscriber Service)

Bu proje, Protobuf kullanarak dağıtık bir abonelik sistemi oluşturmayı amaçlamaktadır. Sistem; sunucular, istemciler, bir yönetim paneli (admin), ve bir veri görselleştirme aracı (plotter) içerir. Her bir bileşen, TCP soketleri üzerinden haberleşir ve Protobuf ile tanımlı mesajlar kullanılarak veri alışverişi yapılmayı amaçlar.


# Proje Bileşenleri
1)Sunucular (Server1, Server2, Server3):
Gelen mesajlara göre abone işlemleri gerçekleştirir ve kapasite durumu gibi bilgileri sağlar. CAPACITY_REQUEST mesajını alarak abone bilgilerini döndürür. Protobuf kullanarak tanımlı Capacity ve Subscriber nesneleriyle çalışır.

2)İstemci (Client):
Sunucularla iletişim kurarak yeni abonelik işlemleri yapar. Sunucular arasında yük devretmeyi destekler.

3)Yönetim Paneli (Admin):
Tüm sunucularla iletişim kurar. Sunuculardan kapasite sorguları yapar. Mesaj gönderim ve test işlemleri gerçekleştirir.

4)Plotter (Veri Görselleştirici):
Sunucuların kapasite bilgilerini grafik üzerinde canlı olarak gösterir.


# Proto Dosyalarının Derlenmesi
Java için:
``` 
cd dist_servers
protoc --java_out=. capacity.proto
protoc --java_out=. subscriber.proto
protoc --java_out=. configuration.proto
```
Python için:
```
cd plotting
protoc --python_out=. capacity.proto
protoc --python_out=. subscriber.proto
protoc --python_out=. configuration.proto
```

# Sunucuların Başlatılması
```
cd dist_servers
javac -cp ".;protobuf-java-4.29.2.jar" -d . Server1.java
java -cp ".;protobuf-java-4.29.2.jar" Server1
```

# Admin Panelinin Çalıştırılması
```
ruby admin.rb
```

# Projenin Çıktı Örnekleri
```
Server1 is listening on port 5001
Server2 is listening on port 5002
Server3 is listening on port 5003
```
```
New client connected to Server1
Server1 received:
Odev Deneme
Unknown message received.

```
```
New client connected to Server2
Server2 received: Ping from Admin to Server2
```
```
New client connected to Server3
Server3 received: CPCTY
```
```
Connecting to Server1 on port 5001...
Subscriber sent to Server1.
Connecting to Server2 on port 5002...
Response from Server2: Processed by Server2: Ping from Admin to Server2
Connecting to Server3 on port 5003...
Capacity response from Server3: [80, 114, 111, 99, 101, 115, 115, 101, 100, 32, 98, 121, 32, 83, 101, 114, 118, 101, 114, 51, 58, 32, 67, 80, 67, 84, 89, 13, 10]
```

# Ekip Üyeleri

- [22060326 Öykü Karagülle](https://github.com/karagulleoyku)
- [22060334 Çiğse Kaya](https://github.com/kayacigse)
- [20060367 Zeynep Sezin Kazancıoğlu](https://github.com/Zsezin)

# Proje Videosu
https://youtu.be/QUwwAKtrdoA
(Projemiz bazı bağlantı hataları yüzünden eksiktir videoda belirtilmiştir.)
