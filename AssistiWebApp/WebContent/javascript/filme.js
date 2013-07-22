$(function(){
	var server = "http://localhost" + ":8080/AssistiWebApp/servico/feed";
	var descricaoFilme = "http://localhost" + ":8080/AssistiWebApp/servico/feed/filme/";
	var filmeEmCartaz = "http://localhost" + ":8080/AssistiWebApp/servico/feed/cartaz";
	var filmeLancamento = "http://localhost" + ":8080/AssistiWebApp/servico/feed/lancamentos";
	var adicionarFilmeAoUsuario = "http://localhost" + ":8080/AssistiWebApp/servico/filme/";
	var removerFilmeDoUsuario = "http://localhost" + ":8080/AssistiWebApp/servico/filme/";
	var filmeUsuario = "http://localhost" + ":8080/AssistiWebApp/servico/filme/usuario";
	var pesquisarFilme = "http://localhost" + ":8080/AssistiWebApp/servico/feed/pesquisa/";
	
	var filmeClicado;
	
	function addFilmeAoHtml(id, titulo, dataLancamento, imagem, listaASerColocadoOElemento){
		var $filme = "<li>" +
						"<a class='lista' class='ui-icon-alt' data-icon='arrow-r' href='#descricaoFilme' id='"+id+"'>" +
						"<div class='idFilme'>" + id + "</div>" +
							"<img src='http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w92"+imagem+"'>"+
							"<h2 >"+titulo+"</h2>"+
							"<p >Lancamento "+dataLancamento+"</p>"+
						"</a>" +
					"</li>";
		
		$(listaASerColocadoOElemento).append($filme);
		$(listaASerColocadoOElemento).listview( "refresh" );
		$("#"+id).on("click",onFilmeClick);
		$(".idFilme").hide();
	}
	
	function addDescricaoFilmeAoHtml(id, titulo, descricao, link, lancamento, popularidade){
		var $filmeDescricao = 
									"<li id="+id+">" +
									"<h2>"+titulo+"</h2>"+
									"<p><strong> Site do Filme "+link+"</strong></p>"+
									"<p>Descricao "+descricao+"</p>"+
									"<p> Lancamento"+lancamento+"</p>"+
									"<p class='ui-li-aside'><strong> Popularidade </strong>"+popularidade+"</p>"+
									"</li>";
		$("#divisaoDaList").append($filmeDescricao);
		$("#descricao").listview("refresh");
		saberSeEstaSendoSeguido();
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
				$("#divisaoDaList").empty();
					
					addDescricaoFilmeAoHtml(resultado.id, resultado.original_title,
							resultado.overview, resultado.homepage,
							resultado.release_date, resultado.popularity);
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
			console.log("RESPOSTA: " + resposta);
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