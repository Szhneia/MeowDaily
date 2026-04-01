**Android Login and Registration App**


**Deskripsi Proyek**

Aplikasi Android ini dibuat untuk memenuhi tugas pengembangan aplikasi mobile. Aplikasi ini mensimulasikan sistem autentikasi pengguna secara lokal, mencakup fitur pendaftaran akun (Register), masuk akun (Login), dan atur ulang kata sandi (Forgot Password). Seluruh data kredensial pengguna disimpan sementara di dalam memori perangkat menggunakan SharedPreferences.


**Fitur Aplikasi**
- Validasi Input Real-time: Mengecek format email dan kekuatan kata sandi secara langsung saat pengguna sedang mengetik.
- Input Form Dinamis: Mengimplementasikan penggunaan CheckBox untuk pilihan hobi dengan syarat minimal pilihan, RadioGroup untuk jenis kelamin, dan Spinner untuk daftar lokasi.
- Interaksi Gestur: Dilengkapi dengan sensor gestur long-press pada tombol tertentu.
- Dialog Konfirmasi: Menampilkan pop-up AlertDialog untuk memverifikasi ulang data pengguna sebelum disimpan ke dalam sistem.
- Penyimpanan Lokal: Menerapkan SharedPreferences untuk mencatat dan memverifikasi email beserta kata sandi pengguna tanpa memerlukan koneksi basis data eksternal.
- Lupa Kata Sandi: Mengizinkan pengguna untuk menimpa kata sandi lama dengan yang baru, dengan syarat email yang dimasukkan sudah terdaftar sebelumnya.


**Teknologi yang Digunakan**
- Bahasa Pemrograman: Kotlin
- Desain Antarmuka: XML
- Penyimpanan Data: SharedPreferences
- Lingkungan Pengembangan: Android Studio


**Panduan Instalasi dan Penggunaan**
- Unduh atau clone repositori kode sumber ini ke komputer lokal.
- Buka aplikasi Android Studio.
- Pilih menu File, kemudian Open, dan arahkan ke direktori proyek ini.
- Tunggu beberapa saat hingga proses sinkronisasi Gradle selesai.
- Tekan tombol Run untuk menjalankan aplikasi pada emulator atau perangkat fisik Android yang telah terhubung.


**Alur Sistem**
1. Register: Pengguna baru diwajibkan mengisi seluruh bidang formulir dengan format yang benar. Setelah berhasil, pengguna akan diarahkan ke halaman Login.
2. Login: Pengguna memasukkan email dan kata sandi yang telah didaftarkan. Sistem akan memvalidasi kecocokan data dengan catatan di SharedPreferences.
3. Forgot Password: Jika pengguna tidak dapat masuk, mereka dapat mengakses halaman ini untuk memasukkan email terdaftar dan membuat kata sandi baru.

**Screenshot UI**
![1](https://github.com/user-attachments/assets/f99e627a-2e1f-41e9-aae6-a5527f6d12ca)
![2](https://github.com/user-attachments/assets/96d4dedb-2127-46f7-84cf-0eee0cfb05c4)
![3](https://github.com/user-attachments/assets/51fc29e7-9144-4a74-95a8-6e17d57862d1)
![4](https://github.com/user-attachments/assets/931e5eb9-feb7-403c-9884-641b73fefa7c)


**Video Demo**

https://github.com/user-attachments/assets/c1988f62-3bf0-4f91-b9ac-861c06dca089

**Pengembang**

Dhenia Putri Nuraini
