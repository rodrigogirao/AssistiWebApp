$(function(){
	
	var server = "http://localhost" + ":8080/AssistiWebApp/servico/feed";
	
	function addFilmeAoHtml(id, titulo, dataLancamento, imagem){
		var $filme = "<li id="+id+">" +
						"<a href=''>" +
							"<img src='http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w92"+imagem+"'>"+
							"<h2>"+titulo+"</h2>"+
							"<p>Lancamento "+dataLancamento+"</p>"+
						"</a>" +
					"</li>";
		
		$("#feedDeFilmes").append($filme);
		$("#feedDeFilmes").listview( "refresh" );
	}
	
	
	
	function listarFilmesDoFeed(){
		$.getJSON(server).done(
				function(resposta){
					console.log("resposta: ", resposta);
					$("#feedDeFilmes").empty();
					var resultado = resposta.results;
					for ( var filme = 0; filme < resultado.length; filme++) {
						console.log("ENTROU NO FOR", filme);
						addFilmeAoHtml( resultado[filme].id,
								resultado[filme].original_title, resultado[filme].release_date,
								resultado[filme].poster_path);
					}
				});
		
	}
	
	$("#feed").click(listarFilmesDoFeed);
	listarFilmesDoFeed();
});