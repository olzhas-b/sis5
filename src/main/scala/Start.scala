import com.google.gson.Gson

import scala.None.getOrElse
import scala.io.Source
import scala.util.control.Breaks.breakable
import scala.util.matching.Regex

object Start extends App {
  var json = new Gson();
  var check1 = new Check();
  val file = Source.fromFile("/home/olzhas/IdeaProjects/Parse_Json/src/main/scala/raw.txt").mkString;
  var filial = "Филиал.*".r;
  var cassa = "Касса.*".r;
  var nds_seria = "№.[0-9]+".r;
  var smena = "Смена.*".r;
  var check_number = "Порядковый.*".r;
  var cassir = "Кассир.*".r;
  var cost = "\\ИТОГО:\\n.*".r;
  check1.filial = (filial).findFirstIn(file).getOrElse("NO Filial");
  check1.cassa = (cassa).findFirstIn(file).getOrElse("NO Cassa");
  check1.nds_seria = (nds_seria).findFirstIn(file).getOrElse("NO seria");
  check1.smena = (smena).findFirstIn(file).getOrElse("NO Смена");
  check1.BIN = ("БИН.*".r).findFirstIn(file).getOrElse("NO BIN");
  check1.check_number = (check_number).findFirstIn(file).getOrElse("No check number");
  check1.cassir = (cassir).findFirstIn(file).getOrElse("NO cassir");
  check1.cost = (cost).findFirstIn(file).getOrElse("NO COST");
  check1.cost = check1.cost.slice(7, check1.cost.length - 3);
  var str = file.split("\n").toArray;
  check1.name = str(0);
  for(i <- 0 until str.length) {
    var isID = true;
    for(j <- 0 until str(i).length) {
      if (str(i).charAt(j) != '.' && !(str(i).charAt(j) >= '0' && str(i).charAt(j) <= '9')) {
        isID = false;
      }
    }
    if(isID == true) {
      var item: Item = new Item();
      var id = str(i).slice(0, str(i).length - 1);
      item.id = id.toInt;
      item.contains = str(i + 1);
      item.size = str(i + 2);
      item.price = str(i + 3).slice(0, str(i + 3).length - 3)
      check1.items.add(item);
    }
  }

  var response = json.toJson(check1);
  println(response)
}
