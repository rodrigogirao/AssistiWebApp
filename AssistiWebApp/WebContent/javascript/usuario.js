$(function() {
	var server = "http://192.168.1.105" + ":8080/AssistiWebApp/servico/usuario";
	
	function cadastrar() {
		$.ajax({
			type:'POST',
			contentType: 'application/json',
			url: server +"",
			data: formUsuarioToJson(),
			success: function(data, textStatus, jqXHR){
				console.log("Resposta: "+ data);
				if(data==="true"){
					$("#conteudoDoDialogo").text("Usuario cadastrado");
					$.mobile.changePage($('#dialogo'));
				}
				if(data==="false"){
					$("#conteudoDoDialogo").text("Usuario existente");
					$.mobile.changePage($('#dialogo'));
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
                //$.mobile.dialog.prototype.options.closeBtnText;
                alert('Erro ao cadastrar: ' + textStatus);
			}
		});
	}
	
	function logar() {
		$.ajax({
			type:'POST',
			contentType: 'application/json',
			url: server +"/login",
			data: formLoginToJson(),
			success: function(data, textStatus, jqXHR){
				console.log("Resposta: "+ data);
				var resposta = data.split("-");
				console.log("Resposta: "+ resposta[0]);
				console.log("Resposta: "+ resposta[1]);
				if(resposta[0]==="true"){
					window.name = data[1];
					$.mobile.changePage($('#feed'));
					
				}
				if(data==="false"){
					alert('Login ou senha incorretos');
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
                //$.mobile.dialog.prototype.options.closeBtnText;
                alert('Erro de conexão: ' + textStatus);
			}
		});
	}
	
	
	
	function formUsuarioToJson(){
		return JSON.stringify({
			"nome": $('#nomeUsuario').val(),
			"senha": $('#senhaUsuario').val(),
			"email": $('#emailUsuario').val(),
			"login": $('#loginUsuario').val(),
		});
	}
	
	function formLoginToJson(){
		return JSON.stringify({
			"login": $('#login').val(),
			"senha": $('#senha').val(),
		});
	}
	
	function addUsuarioAoHTML(id, nome) {

		var $usuario = "<li id="+id+"><a href='#'>"+nome+ "</a></li>";
					
			$("#listaDeUsuarios").append($usuario);
			$("#listaDeUsuarios").listview( "refresh" );
	}

	function carregarUsuarios() {
		$.getJSON(server + "").done(
				function(data) {
					console.log("data: ", data);
					$("#listaDeUsuarios").empty();
					for ( var usuario = 0; usuario < data.length; usuario++) {
						console.log("ENTROU NO FOR", usuario);
						addUsuarioAoHTML( data[usuario].id,
								data[usuario].nome);
					}
				});
	}
	
	$("#listarUsuarios").on('click',carregarUsuarios);
	$("#cadastrarUsuario").on('click',cadastrar);
	$("#fazerLogin").on('click',logar);
});