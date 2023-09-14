package vo;

public class ImageBean {
	
	private int image_id;
	private String filename;
	private String filepath;
	
	public ImageBean () {}

	public ImageBean(int image_id, String filename, String filepath) {
		super();
		this.image_id = image_id;
		this.filename = filename;
		this.filepath = filepath;
	}

	public int getImage_id() {
		return image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
}
