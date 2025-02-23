# IQ Puzzler Pro Solver dengan Pendekatan Algoritma Brute Force
> Tugas Kecil 1 IF 2211 Strategi Algoritma

> Disusun oleh Muhammad Ghifary Komara Putra - 13523066

![wonderhoy](https://github.com/user-attachments/assets/80c80e71-e077-447d-a3d2-8ebf61ff478f)

### Deskripsi Singkat

![image](https://github.com/user-attachments/assets/d356a4d9-50c0-4cf5-a7eb-63c6301617e6)

Selamat datang di program IQPuzzlerProSolver! Program ini merupakan program yang mampu menemukan solusi permainan IQ Puzzler Pro berdasarkan masukan file .txt. Pencarian solusi dilakukan dengan pendekatan algoritma brute force, dengan detail algoritma tertera pada laporan yang tersedia pada folder `doc`.

### Requirement, Instalasi, dan Panduan Penggunaan

Untuk menjalankan program, silakan ikuti langkah-langkah berikut:
1. lakukan instalasi Java 23 dan JavaFX 23
2. kemudian clone repositori ini pada perangkat Anda.
3. Pada CLI, bergeraklah menuju folder bin
4. Jalankan perintah berikut:
  ```java --module-path "path menuju folder lib javafx-sdk-23" --add-modules javafx.controls,javafx.fxml - jar IQPuzzlerProSolver.jar``` 

Contoh:
 
 ```java --module-path "C:\Users\HP\Downloads\openjfx-23.0.1_windows-x64_bin-sdk\javafx-sdk-23.0.1\lib" --add-modules javafx.controls,javafx.fxml -jar IQPuzzlerProSolver.jar```
 
 5. Unggah masukan .txt dengan format sebagai berikut:

 ```
 N M P
 DEFAULT
 Piece ke-1
 Piece ke-2
 .
 .
 .
 Piece ke-P
```

Contoh

```
2 3 3
DEFAULT
AA
 A
B
CC
```

dengan N dan M merupakan dimensi papan permainan

6. Jika hendak menyimpan solusi, silakan klik button yang bersesuaian. File akan tersimpan pada folder `bin/data/output`
7. Selamat menjalankan program!

Catatan: Jika hendak melakukan kompilasi kembali terhadap file .jar, silakan ikuti langkah berikut:
1. Buka CLI pada root directory
2. Jalankan perintah berikut untuk melakukan kompilasi terhadap file .java:
`javac --module-path "path menuju folder lib javafx-sdk-23" --add-modules javafx.controls,javafx.fxml -d bin src/Function/*.java src/GUI/*.java`
3. Untuk melakukan kompilasi menjadi file .jar, jalankan perintah berikut:
`jar cfe bin/IQPuzzlerProSolver.jar GUI.Main -C bin .`

### Cuplikan Program
1. Homepage


![image](https://github.com/user-attachments/assets/d356a4d9-50c0-4cf5-a7eb-63c6301617e6)

2. Layar Solusi


![image](https://github.com/user-attachments/assets/5e9ba6ec-e2c8-4081-a8b3-7d8e6c12456c)
