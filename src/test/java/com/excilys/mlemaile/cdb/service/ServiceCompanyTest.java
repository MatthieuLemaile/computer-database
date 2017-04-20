package com.excilys.mlemaile.cdb.service;

//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath:spring.xml" })
public class ServiceCompanyTest {

    // @Mock
    // CompanyDao mockCompanyDao;
    //
    // @Autowired
    // @InjectMocks
    // private ServiceCompany serviceCompany;

    // @Before
    // public void setup() {
    // MockitoAnnotations.initMocks(this);
    // }

    // @Test
    // public void testListcompanies() {
    // List<Company> companies = new ArrayList<>();
    // companies.add(new Company.Builder().build());
    // CompanyDao mockCompanyDao = Mockito.mock(CompanyDao.class);
    // Mockito.when(mockCompanyDao.listNumberCompaniesStartingAt(10, 0L)).thenReturn(companies);
    // List<Company> companiesReturned = serviceCompany.listcompanies(10, 0L);
    // Mockito.verify(mockCompanyDao).listNumberCompaniesStartingAt(10, 0L);
    // assertEquals("List Companies does not work as intended", companies, companiesReturned);
    //
    // }

    // @Test
    // public void testGetCompany() {
    // Company company = new Company.Builder().build();
    // Optional<Company> opt = Optional.ofNullable(company);
    // CompanyDao mockCompanyDao = mock(CompanyDao.class);
    // DaoFactory mockFactory = mock(DaoFactory.class);
    // Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
    // when(mockFactory.getCompanyDao()).thenReturn(mockCompanyDao);
    // when(mockCompanyDao.getCompanyById(1)).thenReturn(opt);
    // assertEquals("Get Company does not work as
    // intended",company,ServiceCompany.INSTANCE.getCompanyById(1));
    // }
    //
    // @Test
    // public void testDeleteCompanyId(){
    // Company c = new Company.Builder().build();
    // DatabaseConnection mockDatabaseConnection = mock(DatabaseConnection.class);
    // Connection mockConnection = mock(Connection.class);
    // DaoFactory mockFactory = mock(DaoFactory.class);
    // CompanyDao mockCompanyDao = mock(CompanyDao.class);
    // ComputerDao mockComputerDao = mock(ComputerDao.class);
    // Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
    // Whitebox.setInternalState(DatabaseConnection.class, "INSTANCE", mockDatabaseConnection);
    // when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
    // when(mockFactory.getCompanyDao()).thenReturn(mockCompanyDao);
    // when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
    // when(mockCompanyDao.getCompanyById(1)).thenReturn(Optional.ofNullable(c));
    // assertTrue("Delete company does not work as
    // intended",ServiceCompany.INSTANCE.deleteCompany(1));
    // }
}
