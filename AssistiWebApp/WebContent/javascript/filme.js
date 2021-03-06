$(function(){
	var host = "http://rolelinux.cloudapp.net";
	var server = host + "/AssistiWebApp/servico/feed";
	var descricaoFilme = host + "/AssistiWebApp/servico/feed/filme/";
	var filmeEmCartaz = host + "/AssistiWebApp/servico/feed/cartaz";
	var filmeLancamento = host + "/AssistiWebApp/servico/feed/lancamentos";
	var verificarFilmeExiste = host + "/AssistiWebApp/servico/filme/existe/";
	var adicionarFilmeAoUsuario = host + "/AssistiWebApp/servico/filme/";
	var removerFilmeDoUsuario = host + "/AssistiWebApp/servico/filme/";
	var filmeUsuario = host + "/AssistiWebApp/servico/filme/usuario";
	var pesquisarFilme = host + "/AssistiWebApp/servico/feed/pesquisa/";
	
	var filmeClicado;
	
	function addFilmeAoHtml(id, titulo, dataLancamento, imagem, listaASerColocadoOElemento){
		var $filme = "<li>" +
						"<a class='lista' class='ui-icon-alt' data-icon='arrow-r' href='#descricaoFilme' id='"+id+"'>" +
						"<div class='idFilme'>" + id + "</div>" +
							"<img src='http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w92"+imagem+"'>"+
							"<h2 >"+titulo+"</h2>"+
							"<b><p>Lancamento: </b>"+dataLancamento+"</p>"+
						"</a>" +
					"</li>";
		
		$(listaASerColocadoOElemento).append($filme);
		$(listaASerColocadoOElemento).listview( "refresh" );
		$("#"+id).on("click",onFilmeClick);
		$(".idFilme").hide();
	}
	
	function addDescricaoFilmeAoHtml(id, titulo, tituloOriginal, descricao, link, lancamento, popularidade,caminhoImagen){
			var linkpequeno;
			var linkmedio;
			var linkgrande;
			$.get(verificarFilmeExiste+id).done(function(resposta){
				console.log("RESPOSTA VERIFICAEXISTE: " + resposta);
				if(resposta==="true"){
					console.log("ENTROU NO TRUE");
					linkpequeno = "http://portalvhdsvzvk00nyjlrxh.blob.core.windows.net/pequeno";
					linkmedio = "http://portalvhdsvzvk00nyjlrxh.blob.core.windows.net/medio";
					linkgrande = "http://portalvhdsvzvk00nyjlrxh.blob.core.windows.net/grande";
				}else{
					console.log("ENTROU NO ELSE");
					linkpequeno = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w92";
					linkmedio = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w500";
					linkgrande = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/original";
				}

				console.log("LINK PEQUENO: "+linkpequeno);

			var $filmeDescricao = "<h3>"+titulo+"</h3>"+
									"<h5>Titulo Original: "+tituloOriginal+"</h5>"+
									"<p><b>Sinopse</b></p>"+
									"<hr noshade='noshade' />";
									if(descricao==null){
										$filmeDescricao += "<p>Sem sinopse em portugues</p>";
									}else{
										$filmeDescricao += "<p>"+descricao+"</p>";
									}
									if(link==null){
										$filmeDescricao += "<p><strong> Site do Filme: Nao possui</strong></p>";
									}else{
										$filmeDescricao += "<p><strong> Site do Filme: "+link+"</strong></p>";
									}
									$filmeDescricao += "<p><b>Lancamento: </b>"+lancamento+"</p>"+
									"<p class='ui-li-aside'><strong> Popularidade </strong>"+popularidade+"</p>";
									$filmeDescricao += "<h3>Imagem: </h3> <a href='"+linkpequeno+caminhoImagen+"'> Pequena</a> " +
														" <a href='"+linkmedio+caminhoImagen+"'> Media</a> " +
														" <a href='"+linkgrande+caminhoImagen+"'> Grande</a> ";
		$("#divisaoDaList").append($filmeDescricao);
		$("#descricao").listview("refresh");
		saberSeEstaSendoSeguido();
			});

			
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
								resultado[filme].title, resultado[filme].release_date,
								resultado[filme].poster_path, "#feedDeFilmes");
					}
				});
		
	}
	
	function listarDescricaoFilme(id) {
		$.getJSON(descricaoFilme+id).done(
			function(resultado){
				console.log("resultado 2: " ,resultado);
				$("#divisaoDaList").empty();
					
					addDescricaoFilmeAoHtml(resultado.id, resultado.title, resultado.original_title,
							resultado.overview, resultado.homepage,
							resultado.release_date, resultado.popularity, resultado.backdrop_path);
			});
	}
	
	function listarFilmesEmCartaz(){
		console.log("CHEGOU NO LISTA FILMES");
		$.getJSON(filmeEmCartaz).done(
				function(resposta){
					console.log("CHEGOU function NO LISTA FILMES");
					//console.log(resposta);
					$("#listaDeFilmesEmCartaz").empty();
					var resultado = resposta.results;
					for(var filme = 0; filme < resultado.length; filme++){
						addFilmeAoHtml(resultado[filme].id,
								resultado[filme].title, resultado[filme].release_date,
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
								resultado[filme].title, resultado[filme].release_date,
								resultado[filme].poster_path, "#listaDeFilmesLancamentos");
					}
				});
	}
	
	function listarFilmesDoUsuario(){
		$.getJSON(filmeUsuario+"/"+window.name).done(
				function(resposta){
					$("#listaDeFilmesBiblioteca").empty();
					var resultado = resposta;
					for(var filme = 0; filme < resultado.length; filme++){
						addFilmeAoHtml(resultado[filme].id,
								resultado[filme].nome, resultado[filme].dataDeLancamento,
								resultado[filme].classificacao, "#listaDeFilmesBiblioteca");
					}
				});
	}
	
	function onFilmeClick(){
		$lastClicked = $(this);
		console.log("ENTROU NO Onfilmeclick");
		filmeClicado = $lastClicked.children(".idFilme").text();
		console.log("Ultimo clicado",$lastClicked.text());
		console.log("ENTROU NO Onfilmeclick e imprimiu id", filmeClicado);
		listarDescricaoFilme(filmeClicado);
		
	}
	
	function seguirFilme(seguindo){
		if(seguindo==="true"){
			$.post(adicionarFilmeAoUsuario+filmeClicado+'/usuario/'+window.name);
		}
		else{
			$.ajax({
				type:'DELETE',
				url: removerFilmeDoUsuario+filmeClicado+'/usuario/'+window.name+""
				});
		}
	}
	
	
	$("#proximosLancamentos").click(listarFilmesProximosLancamentos);
	$("#tabBiblioteca").on("click", listarFilmesDoUsuario);
	$("#emCartaz").click(listarFilmesEmCartaz);
	$("#feed").click(listarFilmesDoFeed);
	
	function saberSeEstaSendoSeguido(){
		$.get(filmeUsuario+"/"+window.name+"/seguindo/"+filmeClicado).done(function(resposta){
			console.log("RESPOSTA SABERSEESTASEGUINDO: " + resposta);
			if(resposta==="true"){
				$("#botaoSeguir").attr("select", "true");
				$("#botaoSeguir").buttonMarkup({icon:"minus", theme: "b"});
				$("#botaoSeguir").find(".ui-btn-text").text("Seguindo");
			}else{
				$("#botaoSeguir").attr("select", "false");
				$("#botaoSeguir").buttonMarkup({icon:"star", theme: "a"});
				$("#botaoSeguir").find(".ui-btn-text").text("Seguir");
			}
		});
	}
	$("#botaoSeguir").on("click", function(event){
		var selected = $(this).attr("select");
		if(selected==="true"){
			seguirFilme("false");
			$(this).attr("select","false");
			$(this).buttonMarkup({icon:"star", theme: "a"});
			$(this).find(".ui-btn-text").text("Seguir");			
		}
		else{
			seguirFilme("true");
			$(this).attr("select","true");
			$(this).buttonMarkup({icon:"minus", theme: "b"});
			$(this).find(".ui-btn-text").text("Seguindo");
		}
	});
	
	$("#feedDeFilmes").on("listviewbeforefilter", function (e, data){
		var $ul = $( this ),
        $input = $( data.input ),
        value = $input.val(),
        html = "";
		$ul.html( "" );
		console.log("Input",$input);
		console.log("Value", value);
		
		if ( value && value.length > 2 ) {
			$ul.html( "<li><div class='ui-loader'><span class='ui-icon ui-icon-loading'></span></div></li>" );
            $ul.listview( "refresh" );
            $.ajax({
                url: pesquisarFilme + $input.val(),
                dataType: "json",
                crossDomain: true,
            })
            .then( function ( response ) {
            	var resposta = response.results;
                $.each( resposta, function ( i, val ) {
                		html += "<li>" +
                					"<a class='lista' class='ui-icon-alt' data-icon='arrow-r' href='#descricaoFilme' id='pesquisa"+val.id+"'>" +
                					"<div class='idFilme'>" +val.id + "</div>" +
                					"<h2 >"+val.original_title+"</h2>"+
                					"</a>"+
                				"</li>";
                		
                		 console.log("Entrou filme click e id eh: ", val.id);
                		 $("#pesquisa"+val.id).on("click",onFilmeClick);
                });
               
                $ul.html( html );
                $ul.listview( "refresh" );
                $ul.trigger( "updatelayout");
               
                $(".idFilme").hide();
            });
        }
    });
	
	listarFilmesDoFeed();
	
});