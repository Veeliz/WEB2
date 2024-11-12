package com.jsfcourse.calc;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

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
			 double kwotaKredytu = Double.parseDouble(this.kwota);
	            int okresLata = Integer.parseInt(this.lata);
	            double oprocentowanie = Double.parseDouble(this.oprocentowanieMiesieczne);

	            // Oblicz miesięczną stopę procentową
	            double miesiecznaStopa = oprocentowanie / 100;
	            int liczbaRat = okresLata * 12;

	            // Obliczenie miesięcznej raty kredytu
	            wynik = kwotaKredytu * miesiecznaStopa / (1 - Math.pow(1 + miesiecznaStopa, -liczbaRat));


			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
			return true;
		} catch (Exception e) {
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów", null));
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

	// Put result in messages on AJAX call
	public String calc_AJAX() {
		if (doTheMath()) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Wynik: " + result, null));
		}
		return null;
	}

	public String info() {
		return "info";
	}
}
