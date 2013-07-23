package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;

import com.microsoft.windowsazure.services.core.storage.*;
import com.microsoft.windowsazure.services.blob.client.*;

public class Blob {

	// Define the connection-string with your values
	public static final String storageConnectionString = 
	    "DefaultEndpointsProtocol=http;" + 
	    "AccountName=portalvhdsvzvk00nyjlrxh;" + 
	    "AccountKey=anK/cXomy6hQ8IqJnwdwLFfJpRctXvVd3FpOGUV26pbbBGfr6SC+Vke4jTs9gyjRYIbcBcE4AV6q9Zy3KblY+Q==";
	
	private CloudStorageAccount storageAccount;
	private CloudBlobClient blobClient;
	private CloudBlobContainer container;
	
	private void configuracao(String tipoContainer) throws InvalidKeyException, URISyntaxException, StorageException{
		// Retrieve storage account from connection-string
				storageAccount =
				    CloudStorageAccount.parse(storageConnectionString);
				// Create the blob client
				blobClient = storageAccount.createCloudBlobClient();
				
				// Get a reference to a container
				// The container name must be lower case
				container = blobClient.getContainerReference(tipoContainer);

				// Create the container if it does not exist
				container.createIfNotExist();
				
				// Create a permissions object
				BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

				// Include public access in the permissions object
				containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

				// Set the permissions on the container
				container.uploadPermissions(containerPermissions);
	}
	
	private File gravaArquivoDeURL(String stringUrl, String pathLocal) {  
	    try {  
	      
	        //Encapsula a URL num objeto java.net.URL  
	        URL url = new URL(stringUrl);  
	        
	        //Cria streams de leitura (este metodo ja faz a conexao)...  
	        InputStream is = url.openStream();  
	  
	        //... e de escrita.  
	        FileOutputStream fos = new FileOutputStream(pathLocal);  
	  
	        //Le e grava byte a byte. Voce pode (e deve) usar buffers para  
	        //melhor performance (BufferedReader).  
	        int umByte = 0;  
	        while ((umByte = is.read()) != -1){  
	            fos.write(umByte);  
	        }  
	  
	        //Nao se esqueca de sempre fechar as streams apos seu uso!  
	        is.close();  
	        fos.close();  
	  
	        //apos criar o arquivo fisico, retorna referencia para o mesmo  
	        return new File(pathLocal);  
	          
	    } catch (Exception e) {  
	        //Lembre-se de tratar bem suas excecoes, ou elas tambem lhe tratarão mal!  
	        //Aqui so vamos mostrar o stack no stderr.  
	        e.printStackTrace();  
	    }  
	         
	    return null;  
	}
	
	public void apagarArquivo(final String caminho){  
	    new Thread("Apagador de Arquivo"){  
	    	
	    	File f = new File(caminho);
	          
	        @Override  
	        public void run(){  
	            while(true){
	            	boolean deletou = f.delete();
	            	System.out.println("DELETOU: " + deletou);
	    		if(deletou)	break;
	            }
	        }  
	    }.start();  
	}
	
	public void upload(String tipoContainer, String urlBase, String caminho) throws InvalidKeyException, URISyntaxException, StorageException, FileNotFoundException, IOException{

		this.configuracao(tipoContainer);
		// Create or overwrite the "myimage.jpg" blob with contents from a local file
		CloudBlockBlob blob = container.getBlockBlobReference(caminho);
		File arquivo = this.gravaArquivoDeURL(urlBase+caminho, caminho);
		blob.upload(new FileInputStream(arquivo), arquivo.length());		
	}
	
	public void list(String tipoContainer) throws InvalidKeyException, URISyntaxException, StorageException{
		this.configuracao(tipoContainer);
		// Loop over blobs within the container and output the URI to each of them
		for (ListBlobItem blobItem : container.listBlobs()) {
		    System.out.println(blobItem.getUri());
		}
	}
}
