import java.io.File;
import java.io.IOException;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;


public class JPEGCompressor{

  public static void main(String[] args){

    File originalFile = new File("<CAMINHO_DO_ARQUIVO_PARA_COMPRESSAO>");
    File compressedFile = new File("<CAMINHO_QUE_DESEJA_SALVAR_O_ARQUIVO_COMPRIMIDO>");
    System.out.println("Tamanho do arquivo antes da compressão: " + originalFile.length());
    
    try {
      compressJPEGImage(originalFile, compressedFile, 0.5f);
      System.out.println("Tamanho após compressão: "+ compressedImageSize(originalFile, 0.5f));
    } catch (Exception e) {
      System.out.println("Erro: " + e);
    }

  }

  public static void compressJPEGImage(File originalFile, File compressedFile,float compressionQuality) throws IOException {
      
    RenderedImage image = ImageIO.read(originalFile);
    ImageWriter jpegWriter = ImageIO.getImageWritersByFormatName("jpg").next();
    ImageWriteParam jpegWhiteParam = jpegWriter.getDefaultWriteParam();
    jpegWhiteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    jpegWhiteParam.setCompressionQuality(compressionQuality);

    try(ImageOutputStream output = ImageIO.createImageOutputStream(compressedFile)) {
      jpegWriter.setOutput(output);
      IIOImage outputImage = new IIOImage(image, null, null);
      jpegWriter.write(null,outputImage,jpegWhiteParam);
    }


    jpegWriter.dispose();

  }

  public static int compressedImageSize(File originalFile, float compressionQuality) throws IOException {
    byte[] compressedImageBytes;
    
    RenderedImage image = ImageIO.read(originalFile);
    ImageWriter jpegWriter = ImageIO.getImageWritersByFormatName("jpg").next();
    ImageWriteParam jpegWhiteParam = jpegWriter.getDefaultWriteParam();
    jpegWhiteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    jpegWhiteParam.setCompressionQuality(compressionQuality);

    try(ByteArrayOutputStream baos = new ByteArrayOutputStream(); ImageOutputStream output = new MemoryCacheImageOutputStream(baos)){
      jpegWriter.setOutput(output);
      IIOImage outputImage = new IIOImage(image, null, null);
      jpegWriter.write(null,outputImage,jpegWhiteParam);
      compressedImageBytes = baos.toByteArray();
    }

    jpegWriter.dispose();
    return compressedImageBytes.length;
  }


}
