package com.perpustakaan.dto.response;

public class AnggotaResponse {

    private Long id;
    private String nimNis;
    private String nama;
    private String jenisKelamin;
    private String alamat;
    private String noHp;
    private String foto;
    private String status;

    public AnggotaResponse() {
    }

    public AnggotaResponse(Long id,
                           String nimNis,
                           String nama,
                           String jenisKelamin,
                           String alamat,
                           String noHp,
                           String foto,
                           String status) {

        this.id = id;
        this.nimNis = nimNis;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.noHp = noHp;
        this.foto = foto;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getNimNis() { return nimNis; }
    public String getNama() { return nama; }
    public String getJenisKelamin() { return jenisKelamin; }
    public String getAlamat() { return alamat; }
    public String getNoHp() { return noHp; }
    public String getFoto() { return foto; }
    public String getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setNimNis(String nimNis) { this.nimNis = nimNis; }
    public void setNama(String nama) { this.nama = nama; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
    public void setFoto(String foto) { this.foto = foto; }
    public void setStatus(String status) { this.status = status; }
}