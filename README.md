# Dağıtık Abonelik Sistemi (Distributed Subscriber Service)

Bu proje, Protobuf kullanarak dağıtık bir abonelik sistemi oluşturmayı amaçlamaktadır. Sistem; sunucular, istemciler, bir yönetim paneli (admin), ve bir veri görselleştirme aracı (plotter) içerir. Her bir bileşen, TCP soketleri üzerinden haberleşir ve Protobuf ile tanımlı mesajlar kullanılarak veri alışverişi yapılır.

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
java Server1.java
java Server2.java
java Server3.java
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
Server1 received: ↕
Odev Deneme↑☺
```
```
New client connected to Server2
Server2 received: Ping from Admin to Server2
```
```
New client connected to Server3
Server3 received: REQUEST_CAPACITY
```

# Ekip Üyeleri

- [22060326 Öykü Karagülle](https://github.com/karagulleoyku)
- [22060334 Çiğse Kaya](https://github.com/kayacigse)
- [20060367 Zeynep Sezin Kazancıoğlu](https://github.com/Zsezin)

# Proje Videosu

