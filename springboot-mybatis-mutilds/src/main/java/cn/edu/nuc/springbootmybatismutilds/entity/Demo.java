package cn.edu.nuc.springbootmybatismutilds.entity;

public class Demo {
	private Long id;
	private String title;
	private String descs;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	
	@Override
	public String toString() {
		return "Demo [id=" + id + ", title=" + title + ", descs=" + descs + "]";
	}
	
	
}
