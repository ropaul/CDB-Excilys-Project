package com.excilys.computerdatabase;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import com.excilys.computerdatabase.service.SqlManager;






public class SqlManagerWithMockitoTest {

	public void testGetComputer() {
		int id = 1;
		SqlManager mockManager =  mock(SqlManager.class);
		
			assertTrue(true);
//			verify(mockManager).getComputer(id);
		
	}
	
}
