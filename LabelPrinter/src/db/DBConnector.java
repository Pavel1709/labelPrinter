/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;
//import com.ibm.as400.access.AS400JDBCDriver;
import java.sql.*;
import java.util.ArrayList;
import labelprinter.LabelPrinter;
/**
 *
 * @author pmolchanov
 */
public class DBConnector {
    public static void connect() throws ClassNotFoundException, SQLException {
        try{
            System.out.println("Trying to connect");
            String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
            String URL = "jdbc:as400://";
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, "","" );
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery("with b2cfakeprint(rownumber, codpal, edipro, codser, numsup)\n" +
"as (\n" +
"select row_number() over(partition by pd.edipro) rownumber, pe.codpal, pd.edipro, codser, pe.numsup\n" +
"from fge50fmck4.gesupe pe\n" +
"inner join fge50fmck4.gesupd pd on pe.numsup = pd.numsup and pe.snusup = pd.snusup and pe.codact = pd.codact\n" +
"inner join fge50fmck4.gepro pro on pd.codpro = pro.codpro and pd.codact = pro.codact\n" +
"left join fge50fmck4.geseri seri on pd.codpro = seri.codpro and substr(seri.codser, 1, 6) = 'FAKEDM' and seri.etaser = '20'\n" +
"where pe.cliliv = 'MASSIFICATION'\n" +
"and pe.typsup <> '3'\n" +
"and pe.etasup = '50'\n" +
"and pd.codlot <> 'MARK'\n" +
"and pro.codmdp = 'SER'\n" +
"and pe.codact = 'IDB'\n" +
"and pe.codpal = '" + labelprinter.LabelPrinter.code + "'\n" +
"group by pe.codpal, pd.edipro, codser, pe.numsup\n" +
"),\n" +
"b2bfakeprint(rownumber, codpal, edipro, codser, numsup)\n" +
"as (\n" +
"select row_number() over(partition by pd.edipro) rownumber, pe.codpal, pd.edipro, codser, pe.numsup\n" +
"from fge50fmck4.gesupe pe\n" +
"inner join fge50fmck4.gesupd pd on pe.numsup = pd.numsup and pe.snusup = pd.snusup and pe.codact = pd.codact\n" +
"inner join fge50fmck4.gepro pro on pd.codpro = pro.codpro and pd.codact = pro.codact\n" +
"left join fge50fmck4.geseri seri on pd.codpro = seri.codpro and substr(seri.codser, 1, 6) = 'FAKEDM' and seri.etaser = '20'\n" +
"where pe.cliliv <> 'MASSIFICATION'\n" +
"and pe.typsup <> '3'\n" +
"and pd.codlot <> 'MARK'\n" +
"and pro.codmdp = 'SER'\n" +
"and pe.codact = 'IDB'\n" +
"and pe.refliv = '" + labelprinter.LabelPrinter.code + "'\n" +
"group by pe.codpal, pd.edipro, codser, pe.numsup\n" +
")\n" +
"select trim(c.edipro) as edipro, trim(c.codser) as codser from b2cfakeprint c\n" +
"where c.rownumber <= 10\n" +
"union all\n" +
"select trim(b.edipro)  as edipro, trim(b.codser) as codser from b2bfakeprint b\n" +
"where b.rownumber <= 10\n" +
"order by edipro, codser");
            while(rs.next()) {
                if(!LabelPrinter.hm.containsKey(rs.getString("EDIPRO"))) {
                    LabelPrinter.hm.put(rs.getString("EDIPRO"), new ArrayList<String>());
                    LabelPrinter.hm.get(rs.getString("EDIPRO")).add(rs.getString("CODSER"));
                }
                else {
                    LabelPrinter.hm.get(rs.getString("EDIPRO")).add(rs.getString("CODSER"));
                }
            }
           conn.close();       
        }
        catch(Exception e) {
            System.out.println(e);
        }            
    }   
}
