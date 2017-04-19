package com.excilys.mlemaile.cdb.service;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ComputerDao.class,DaoFactory.class,Computer.Builder.class})
public class ServiceComputerTest {

    // @Test
    // public void testCreateComputer() {
    // ComputerDao mockComputerDao = mock(ComputerDao.class);
    // DaoFactory mockFactory = mock(DaoFactory.class);
    // Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
    // when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
    // assertEquals("create computer does not work as
    // intended",true,ServiceComputer.INSTANCE.createComputer("", null, null, 0));
    // }
	/*
	@Test
	public void testCreateComputerIllegalArgument() {
		//TODO comment réellement mocke le Builder ??
		Computer computer = new Computer.Builder("").build();
		Computer.Builder computerBuilder = mock(Computer.Builder.class);
		ComputerDao mockComputerDao = mock(ComputerDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
		when(mockComputerDao.createComputer(computer)).thenReturn(true);
		when(computerBuilder.build()).thenThrow(new IllegalArgumentException(""));
		assertEquals("create computer does not work as intended",false,ServiceComputer.INSTANCE.createComputer("", null, null, 0));
	}
	*/
    // @Test
    // public void testUpdatecomputer() {
    // Computer computer = new Computer.Builder("").build();
    // ComputerDao mockComputerDao = mock(ComputerDao.class);
    // DaoFactory mockFactory = mock(DaoFactory.class);
    // Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
    // when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
    // assertEquals("Update Computer does not work as
    // intended",true,ServiceComputer.INSTANCE.updatecomputer(computer));
    // }
    //
    // @Test
    // public void testListComputer() {
    // List<Computer> computers = new ArrayList<>();
    // computers.add(new Computer.Builder("").build());
    // ComputerDao mockComputerDao = mock(ComputerDao.class);
    // DaoFactory mockFactory = mock(DaoFactory.class);
    // Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
    // when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
    // when(mockComputerDao.listSortSearchNumberComputer(10, 0,FieldSort.NAME,
    // null)).thenReturn(computers);
    // assertEquals("List computers does not work as
    // intended",computers,ServiceComputer.INSTANCE.listSortSearchNumberComputer(10,
    // 0,FieldSort.NAME, null));
    // }
    //
    // @Test
    // public void testGetComputer() {
    // Computer computer = new Computer.Builder("").build();
    // ComputerDao mockComputerDao = mock(ComputerDao.class);
    // DaoFactory mockFactory = mock(DaoFactory.class);
    // Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
    // when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
    // when(mockComputerDao.getComputerById(1)).thenReturn(Optional.ofNullable(computer));
    // assertEquals("Get computer does not work as
    // intended",computer,ServiceComputer.INSTANCE.getComputerById(1).get());
    //
    // }
    //
    // @Test
    // public void testDeleteComputer() {
    // Computer computer = new Computer.Builder("").id(2).build();
    // ComputerDao mockComputerDao = mock(ComputerDao.class);
    // DaoFactory mockFactory = mock(DaoFactory.class);
    // Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
    // when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
    // assertEquals("Update Computer does not work as
    // intended",true,ServiceComputer.INSTANCE.deleteComputer(computer));
    // }

}
