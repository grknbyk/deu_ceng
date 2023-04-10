import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FileFinder {
	private Path rootDirectory;
	private AList<Path> files;
	private boolean initialize;

	public boolean isInitialized() {
		return initialize;
	}

	// optional current directory constructor
	public FileFinder(String rootDirectory) {
		this.rootDirectory = FileSystems.getDefault().getPath(rootDirectory);
		this.files = new AList<>();
		this.initialize = true;
		try {
			loadDirectories();
		} catch (Exception e) {
			this.initialize = false;
		}
	}

	// current directory constructor
	public FileFinder() {
		this("");
	}

	public AList<Path> getFiles() {
		return files;
	}

	private void loadDirectories() {
		try (DirectoryStream<Path> contents = Files.newDirectoryStream(rootDirectory)) {
			for (Path file : contents)
				files.add(file);
		} catch (NoSuchFileException e) {
			e.printStackTrace();
			System.out.println("'" + rootDirectory + "' : file not found!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error ocurred!");
		}
	}

	public Iterator<Path> getFileIterator() {
		return new FileIterator();
	}

	private class FileIterator implements Iterator<Path> {
		private int currentIndex;
		private int numberLeft;

		private FileIterator() {
			currentIndex = 1;
			numberLeft = files.getLength();
		}

		public boolean hasNext() {
			return numberLeft > 0;
		}

		public Path next() {
			Path result = null;
			if (hasNext()) {
				result = files.getEntry(currentIndex);
				numberLeft--;
				currentIndex++;
			} else
				throw new NoSuchElementException();
			return result;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
