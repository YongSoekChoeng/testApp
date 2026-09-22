package kr.co.gnx.comm.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;
import kr.co.gnx.system.file.FileVO;

public class ImageUtil {
	public void CameraImageChamge(FileVO filevo) {
		// 1. 원본 파일을 읽는다.
		// 2. 원본 파일의 Orientation 정보를 읽는다.
		// 3. 변경할 값들을 설정한다.
		// 4. 회전하여 생성할 파일을 만든다.
		// 5. 원본파일을 회전하여 파일을 저장한다.

		// 1. 원본 파일을 읽는다.
		File imageFile = new File(filevo.getFile_path());

		// 2. 원본 파일의 Orientation 정보를 읽는다.
		int orientation = 1; // 회전정보, 1. 0도, 3. 180도, 6. 270도, 8. 90도 회전한 정보
		int width = 0; // 이미지의 가로폭
		int height = 0; // 이미지의 세로높이
		int tempWidth = 0; // 이미지 가로, 세로 교차를 위한 임의 변수

		Metadata metadata; // 이미지 메타 데이터 객체
		Directory directory; // 이미지의 Exif 데이터를 읽기 위한 객체
		JpegDirectory jpegDirectory; // JPG 이미지 정보를 읽기 위한 객체

		try {
			metadata = ImageMetadataReader.readMetadata(imageFile);
			directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
			if(directory != null){
				orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION); // 회전정보
				width = jpegDirectory.getImageWidth(); // 가로
				height = jpegDirectory.getImageHeight(); // 세로
			}
			
			// 3. 변경할 값들을 설정한다.
		    AffineTransform atf = new AffineTransform();
		    switch (orientation) {
		    case 1:
		        break;
		    case 2: // Flip X
		    	atf.scale(-1.0, 1.0);
		    	atf.translate(-width, 0);
		        break;
		    case 3: // PI rotation 
		    	atf.translate(width, height);
		    	atf.rotate(Math.PI);
		        break;
		    case 4: // Flip Y
		    	atf.scale(1.0, -1.0);
		    	atf.translate(0, -height);
		        break;
		    case 5: // - PI/2 and Flip X
		    	atf.rotate(-Math.PI / 2);
		    	atf.scale(-1.0, 1.0);
		        break;
		    case 6: // -PI/2 and -width
		    	atf.translate(height, 0);
		    	atf.rotate(Math.PI / 2);
		        break;
		    case 7: // PI/2 and Flip
		    	atf.scale(-1.0, 1.0);
		    	atf.translate(-height, 0);
		    	atf.translate(0, width);
		    	atf.rotate(  3 * Math.PI / 2);
		        break;
		    case 8: // PI / 2
		    	atf.translate(0, width);
		    	atf.rotate(  3 * Math.PI / 2);
		        break;
		    }
		    
		    switch (orientation) {
			case 5:
			case 6:
			case 7:
			case 8:
		        tempWidth = width;
		        width = height;
		        height = tempWidth;
				break;
			}
		    
			BufferedImage image = ImageIO.read(imageFile);
			final BufferedImage afterImage = new BufferedImage(width, height, image.getType());
			final AffineTransformOp rotateOp = new AffineTransformOp(atf, AffineTransformOp.TYPE_BILINEAR);
			final BufferedImage rotatedImage = rotateOp.filter(image, afterImage);
			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
		    ImageWriter writer = iter.next();
		    ImageWriteParam iwp = writer.getDefaultWriteParam();
		    iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		    iwp.setCompressionQuality(1.0f);

		    // 4. 회전하여 생성할 파일을 만든다.
		    File outFile = new File(filevo.getFile_path());
		    FileImageOutputStream fios = new FileImageOutputStream(outFile);
		    
		    // 5. 원본파일을 회전하여 파일을 저장한다.
		    writer.setOutput(fios);
		    
		    //imageFile.delete();
		    writer.write(null, new IIOImage(rotatedImage ,null,null),iwp);
		    fios.close();
		    writer.dispose();
			
		} catch (ImageProcessingException e) {
			e.printStackTrace();
		} catch (MetadataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
