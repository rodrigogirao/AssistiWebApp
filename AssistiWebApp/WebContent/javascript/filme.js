$(function(){
	
	var server = "http://localhost" + ":8080/AssistiWebApp/servico/feed";
	var descricaoFilme = "http://localhost" + ":8080/AssistiWebApp/servico/feed/filme/";
	var filmeEmCartaz = "http://localhost" + ":8080/AssistiWebApp/servico/feed/cartaz";
	var filmeLancamento = "http://localhost" + ":8080/AssistiWebApp/servico/feed/lancamentos";
	
	function addFilmeAoHtml(id, titulo, dataLancamento, imagem, listaASerColocadoOElemento){
		var $filme = "<li>" +
						"<a href='#descricaoFilme' id='"+id+"'>" +
						"<div class='idFilme'>" + id + "</div>" +
							"<img src='http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w92"+imagem+"'>"+
							"<h2>"+titulo+"</h2>"+
							"<p>Lancamento "+dataLancamento+"</p>"+
						"</a>" +
					"</li>";
		
		$(listaASerColocadoOElemento).append($filme);
		$(listaASerColocadoOElemento).listview( "refresh" );
		$("#"+id).on("click",onFilmeClick);
		$(".idFilme").hide();
	}
	
	function addDescricaoFilmeAoHtml(id, titulo, descricao, link, lancamento, popularidade){
		var $filmeDescricao = "<li data-role='list-divider'>"+titulo+"</li>" +
									"<li id="+id+">" +
									"<a href=''>" +
									"<h2>"+titulo+"</h2>"+
									"<p><strong> Site do Filme "+link+"</strong></p>"+
									"<p>Descricao "+descricao+"</p>"+
									"<p> Lancamento"+lancamento+"</p>"+
									"<p class='ui-li-aside'><strong> Popularidade </strong>"+popularidade+"</p>"+
									"</a>" +
									"</li>";
		$("#descricao").append($filmeDescricao);
		$("#descricao").listview("refresh");
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
								resultado[filme].poster_path, "#feedDeFilmes");
					}
				});
		
	}
	
	function listarDescricaoFilme(id) {
		$.getJSON(descricaoFilme+id).done(
			function(resultado){
				console.log("resultado 2: " ,resultado);
				$("#descricao").empty();
					
					addDescricaoFilmeAoHtml(resultado.id, resultado.original_title,
							resultado.overview, resultado.homepage,
							resultado.release_date, resultado.popularity);
			});
	}
	
	function listarFilmesEmCartaz(){
		$.getJSON(filmeEmCartaz).done(
				function(resposta){
					$("#listaDeFilmesEmCartaz").empty();
					var resultado = resposta.results;
					for(var filme = 0; filme < resultado.length; filme++){
						addFilmeAoHtml(resultado[filme].id,
								resultado[filme].original_title, resultado[filme].release_date,
								resultado[filme].poster_path, "#listaDeFilmesEmCartaz");
					}
				});
	}
	
	function listarFilmesProximosLancamentos(){
		$.getJSON(filmeLancamento).done(
				function(resposta){
					$("#listaDeFilmesLancamentos").empty();
					var resultado = resposta.results;
					for(var filme = 0; filme < resultado.length; filme++){
						addFilmeAoHtml(resultado[filme].id,
								resultado[filme].original_title, resultado[filme].release_date,
								resultado[filme].poster_path, "#listaDeFilmesLancamentos");
					}
				});
	}
	
	function onFilmeClick(){
		$lastClicked = $(this);
		console.log("ENTROU NO Onfilmeclick");
		var id = $lastClicked.children(".idFilme").text();
		console.log("Ultimo clicado",$lastClicked.text());
		console.log("ENTROU NO Onfilmeclick e imprimiu id", id);
		listarDescricaoFilme(id);
		
	}
	
	$("#proximosLancamentos").click(listarFilmesProximosLancamentos);
	$("#emCartaz").click(listarFilmesEmCartaz);
	$("#feed").click(listarFilmesDoFeed);
	listarFilmesDoFeed();
	
});