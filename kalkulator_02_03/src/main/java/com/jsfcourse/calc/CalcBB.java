package com.jsfcourse.calc;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
//@SessionScoped
public class CalcBB {
    private String kwota;
    private String lata;
    private String oprocentowanieMiesieczne;
    private Double wynik;

    @Inject
    FacesContext ctx;

    public String getKwota() {
        return kwota;
    }

    public void setKwota(String kwota) {
        this.kwota = kwota;
    }

    public String getLata() {
        return lata;
    }

    public void setLata(String lata) {
        this.lata = lata;
    }

    public String getOprocentowanieMiesieczne() {
        return oprocentowanieMiesieczne;
    }

    public void setOprocentowanieMiesieczne(String oprocentowanieMiesieczne) {
        this.oprocentowanieMiesieczne = oprocentowanieMiesieczne;
    }

    public Double getWynik() {
        return wynik;
    }

    public void setWynik(Double wynik) {
        this.wynik = wynik;
    }

    public boolean doTheMath() {
        try {
            // Walidacja: Kwota kredytu nie może być mniejsza niż 1000
            if (kwota == null || kwota.isEmpty()) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kwota nie może być pusta", null));
                return false;
            }

            double kwotaKredytu = Double.parseDouble(this.kwota);

            if (kwotaKredytu < 1000) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kwota kredytu nie może być mniejsza niż 1000", null));
                return false;
            }

            // Walidacja: Sprawdź poprawność lat i oprocentowania
            if (lata == null || lata.isEmpty()) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Okres kredytowania nie może być pusty", null));
                return false;
            }

            if (oprocentowanieMiesieczne == null || oprocentowanieMiesieczne.isEmpty()) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Oprocentowanie miesięczne nie może być puste", null));
                return false;
            }

            // Walidacja: Okres kredytowania musi być większy niż 0
            int okresLata = Integer.parseInt(this.lata);
            if (okresLata <= 0) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Okres kredytowania musi być większy niż 0", null));
                return false;
            }

            // Walidacja: Oprocentowanie musi być większe niż 0
            double oprocentowanie = Double.parseDouble(this.oprocentowanieMiesieczne);
            if (oprocentowanie <= 0) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Oprocentowanie musi być większe niż 0", null));
                return false;
            }

            // Oblicz miesięczną stopę procentową
            double miesiecznaStopa = oprocentowanie / 100;
            int liczbaRat = okresLata * 12;

            // Obliczenie miesięcznej raty kredytu
            wynik = kwotaKredytu * miesiecznaStopa / (1 - Math.pow(1 + miesiecznaStopa, -liczbaRat));

            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
            return true;
        } catch (NumberFormatException e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd w formacie liczb: " + e.getMessage(), null));
            return false;
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów", null));
            return false;
        }
    }

    // Go to "showresult" if ok
    public String calc() {
        if (doTheMath()) {
            return "showresult";
        }
        return null;
    }

    public String info() {
        return "info";
    }
}