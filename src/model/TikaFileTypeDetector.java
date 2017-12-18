package model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypes;

public class TikaFileTypeDetector extends FileTypeDetector {
	private final Tika tika = new Tika();

	public TikaFileTypeDetector() {
		super();
	}

	@Override
	/**
	 * A l'aide de la lib appache tika, essaie de trouver le type du fichier designé
	 * par path
	 * 
	 * @param Path
	 *            le path du fichier dont on veut connaitre le type
	 * 
	 * @return String le type du fichier inféré par Tika
	 */
	public String probeContentType(Path path) throws IOException {
		// Try to detect based on the file name only for efficiency
		String fileNameDetect = tika.detect(path.toString());
		if (!fileNameDetect.equals(MimeTypes.OCTET_STREAM)) {
			return fileNameDetect;
		}

		// Then check the file content if necessary
		String fileContentDetect = tika.detect(path.toFile());
		if (!fileContentDetect.equals(MimeTypes.OCTET_STREAM)) {
			return fileContentDetect;
		}

		// Specification says to return null if we could not 
		// conclusively determine the file type
		return "unkown type";
	}

}