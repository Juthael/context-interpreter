package com.tregouet.context_interpreter.io.inputs.exceptions;

public class FileReaderException extends Exception {

	private static final long serialVersionUID = -4235814332881785363L;

	public FileReaderException() {
	}

	public FileReaderException(String arg0) {
		super(arg0);
	}

	public FileReaderException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FileReaderException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public FileReaderException(Throwable arg0) {
		super(arg0);
	}

}
