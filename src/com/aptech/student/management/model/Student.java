package com.aptech.student.management.model;

public class Student {
	private long id;
	private String name;
	private String palace;
	private boolean gender;
	private String dateOfBirth;
	private float math;
	private float physical;
	private float chemistry;

	public Student(long id, String name, String palace, boolean gender,
			String dateOfBirth, float math, float physical,
			float chemistry) {
		super();
		this.id = id;
		this.name = name;
		this.palace = palace;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.math = math;
		this.physical = physical;
		this.chemistry = chemistry;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPalace() {
		return palace;
	}

	public void setPalace(String palace) {
		this.palace = palace;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public float getMath() {
		return math;
	}

	public void setMath(float math) {
		this.math = math;
	}

	public float getPhysical() {
		return physical;
	}

	public void setPhysical(float physical) {
		this.physical = physical;
	}

	public float getChemistry() {
		return chemistry;
	}

	public void setChemistry(float chemistry) {
		this.chemistry = chemistry;
	}
}
