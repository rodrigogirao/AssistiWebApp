$(function() {
	var server = "http://192.168.1.105" + ":8080/AssistiWebApp/servico/usuario";
	
	function cadastrar() {
		
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
	
	$("#listarUsuarios").click(carregarUsuarios);
	$("#cadastrarUsuario").click(cadastrar);
});