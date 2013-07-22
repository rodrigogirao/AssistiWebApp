package util;

import java.util.List;

public class ResultadosAPI {
	
	private int page;
	private List<FilmeAPI> results;
	private int total_pages;
	private int total_results;
	public int getPage() {
		return page;
	}
	public List<FilmeAPI> getResults() {
		return results;
	}
	public int getTotal_pages() {
		return total_pages;
	}
	public int getTotal_results() {
		return total_results;
	}
}
