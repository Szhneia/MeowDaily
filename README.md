# MeowDaily Seminar App

## Deskripsi Proyek

Aplikasi Android ini dibuat untuk memenuhi tugas UTS pengembangan aplikasi mobile. Aplikasi ini bernama **MeowDaily**, yaitu aplikasi seminar bertema kucing yang memiliki fitur login, register, forgot password, halaman utama, daftar seminar, form pendaftaran seminar, validasi input, dialog konfirmasi, halaman hasil pendaftaran, history seminar, profile pengguna, edit profile, dan logout.

Aplikasi ini menggunakan penyimpanan lokal dengan **SharedPreferences** untuk menyimpan data akun, data profile, data pendaftaran seminar, current seminar, dan history seminar.

## Fitur Aplikasi

### 1. Login

Pengguna dapat login menggunakan email dan password yang sudah didaftarkan. Jika email atau password kosong, aplikasi akan menampilkan pesan peringatan. Jika data tidak sesuai, aplikasi akan menampilkan pesan bahwa email atau password salah atau belum terdaftar.

### 2. Register

Pengguna baru dapat membuat akun melalui halaman register. Pada halaman ini pengguna mengisi nama, email, password, konfirmasi password, jenis kelamin, hobi, dan lokasi.

Setelah registrasi berhasil, pengguna akan diarahkan kembali ke halaman login dan harus login menggunakan akun baru yang sudah dibuat.

### 3. Forgot Password

Fitur forgot password digunakan untuk mengganti password lama dengan password baru berdasarkan email yang sudah terdaftar.

### 4. Halaman Utama

Halaman utama menampilkan sapaan kepada pengguna, foto profile di kanan atas, search bar, beberapa card seminar, dan navigation bar.

Nama dan foto profile akan mengikuti data pengguna yang sedang aktif.

### 5. Daftar Seminar

Aplikasi menampilkan daftar seminar dalam bentuk card. Setiap card berisi gambar seminar, judul seminar, deskripsi singkat, tanggal seminar, dan tombol **Join Seminar**.

### 6. Search Seminar

Pada halaman seminar list, pengguna dapat mencari seminar berdasarkan judul atau deskripsi seminar.

Jika seminar tidak ditemukan, aplikasi akan menampilkan pesan:

**Seminar title/description not found**

### 7. Form Pendaftaran Seminar

Pengguna dapat mengisi form pendaftaran seminar yang berisi:

- Nama
- Email
- Nomor HP
- Jenis kelamin
- Pilihan seminar
- Checkbox persetujuan

Jika pengguna memilih seminar dari halaman utama atau seminar list, maka pilihan seminar akan otomatis terisi sesuai seminar yang dipilih.

### 8. Validasi Input Real-Time

Aplikasi menerapkan validasi input secara real-time. Error akan langsung muncul saat pengguna mengetik data yang tidak sesuai.

Validasi yang diterapkan:

- Semua field wajib diisi.
- Email harus valid dan mengandung karakter `@`.
- Nomor HP hanya boleh berisi angka.
- Nomor HP harus memiliki panjang 10 sampai 13 digit.
- Nomor HP harus diawali dengan angka `08`.
- Checkbox persetujuan wajib dicentang sebelum submit.

Jika data belum lengkap atau tidak sesuai, aplikasi akan menampilkan error atau popup peringatan.

### 9. Dialog Konfirmasi

Setelah semua data valid, aplikasi akan menampilkan dialog konfirmasi sebelum data disimpan.

Dialog berisi pertanyaan:

**Apakah data yang Anda isi sudah benar?**

Jika pengguna memilih **Ya**, data akan disimpan dan pengguna diarahkan ke halaman hasil.  
Jika pengguna memilih **Tidak**, pengguna tetap berada di halaman form.

### 10. Halaman Hasil

Halaman hasil menampilkan data pendaftaran seminar yang sudah diisi oleh pengguna, seperti nama, email, nomor HP, gender, dan seminar yang dipilih.

### 11. History Seminar

Halaman history menampilkan seminar yang sedang diikuti dan riwayat seminar sebelumnya.

Terdapat dua bagian:

- Current Seminar
- Previous Seminar

Card pada halaman history dibuat sama dengan card pada halaman seminar list agar tampilan lebih konsisten.

### 12. Profile Pengguna

Pengguna dapat melihat dan mengedit profile, seperti nama, bio, dan foto profile. Perubahan profile akan tampil juga di halaman lain seperti Home, Seminar List, dan History.

### 13. Logout

Aplikasi menyediakan fitur logout. Setelah logout, pengguna akan diarahkan kembali ke halaman login.

## Teknologi yang Digunakan

- Bahasa Pemrograman: Kotlin
- Desain Antarmuka: XML
- Penyimpanan Data: SharedPreferences
- Lingkungan Pengembangan: Android Studio
- Komponen Android:
  - Intent
  - AlertDialog
  - TextWatcher
  - Spinner
  - RadioGroup
  - CheckBox
  - ImageView
  - Navigation Bar

## Panduan Instalasi dan Penggunaan

1. Unduh atau clone repository kode sumber ini ke komputer lokal.
2. Buka aplikasi Android Studio.
3. Pilih menu **File**, kemudian pilih **Open**.
4. Arahkan ke direktori project ini.
5. Tunggu hingga proses sinkronisasi Gradle selesai.
6. Jalankan aplikasi menggunakan emulator atau perangkat Android.
7. Register akun terlebih dahulu.
8. Login menggunakan akun yang sudah dibuat.
9. Gunakan fitur aplikasi seperti melihat seminar, mendaftar seminar, melihat hasil, melihat history, mengedit profile, dan logout.

## Alur Sistem

### Register

Pengguna baru mengisi form registrasi dengan data yang valid. Setelah registrasi berhasil, pengguna diarahkan kembali ke halaman login.

### Login

Pengguna memasukkan email dan password yang sudah didaftarkan. Sistem akan mencocokkan data tersebut dengan data yang tersimpan di SharedPreferences.

### Home

Setelah login berhasil, pengguna masuk ke halaman utama dan dapat melihat beberapa seminar yang tersedia.

### Seminar List

Pengguna dapat melihat semua daftar seminar dan mencari seminar berdasarkan judul atau deskripsi.

### Form Pendaftaran

Pengguna mengisi data pendaftaran seminar. Sistem akan melakukan validasi input sebelum data dikirim.

### Dialog Konfirmasi

Jika data valid, aplikasi menampilkan dialog konfirmasi sebelum data disimpan.

### Result

Setelah konfirmasi, aplikasi menampilkan hasil pendaftaran seminar.

### History

Data seminar yang sudah didaftarkan akan muncul pada halaman history.

### Profile

Pengguna dapat mengedit nama, bio, dan foto profile.

### Logout

Pengguna dapat keluar dari akun dan kembali ke halaman login.

## Video Penjelasan

Video penjelasan kode dan tampilan aplikasi dapat dilihat melalui link berikut:

[Video Penjelasan Aplikasi MeowDaily](https://youtu.be/nQY1vu_SpPw?si=4fTNCKQAXxKW7TVo)

Video tersebut berisi:

1. Penjelasan halaman Login
2. Penjelasan halaman Utama
3. Penjelasan form Pendaftaran
4. Penjelasan validasi real-time error
5. Penjelasan dialog konfirmasi
6. Penjelasan halaman Hasil
7. Penjelasan kode program

## Pengembang

Dhenia Putri Nuraini
