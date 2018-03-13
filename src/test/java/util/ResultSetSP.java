package util;

import java.sql.ResultSet;

public class ResultSetSP {
	
	private ResultSet resultSet;
	private int codigoError;
	private String msnError;
	
	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public int getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(int codigoError) {
		this.codigoError = codigoError;
	}

	public String getMsnError() {
		return msnError;
	}

	public void setMsnError(String msnError) {
		this.msnError = msnError;
	}

	@Override
	public String toString() {
		return "ResultSetSP [resultSet=" + resultSet + ", codigoError=" + codigoError + ", msnError=" + msnError + "]";
	}

}
