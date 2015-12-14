import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;

import java.sql.ResultSet;


/**
 * Created by vfedotov on 12/14/2015.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ActorSystem _system = ActorSystem.create("AkkaHelloWorld");
        ActorRef master = _system.actorOf(new Props(MasterActor.class), "master");
        String sqlQuery = "SELECT * FROM posts";
//        master.tell(sqlQuery);
//        master.tell(new Result());
        Timeout timeout = new Timeout(Duration.parse("5 seconds"));
        Future<Object> future = Patterns.ask(master, new Result(sqlQuery), timeout);
        ResultSet result = (ResultSet) Await.result(future, timeout.duration());
        while (result.next()) {
            System.out.println("id="+result.getInt("id"));
            System.out.println("body="+result.getString("body"));
        }
        System.out.println(result);
//        future.onSuccess(new PartialFunction<Object, Object>(){
//
//
//            public <A> Function1<A, Object> compose(Function1<A, A> g) {
//                return super.compose(g);
//            }
//
//            public boolean isDefinedAt(Object x) {
//                return false;
//            }
//
//            public <A1, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
//                return super.orElse(that);
//            }
//
//            public Object apply(Object v1) {
//                return null;
//            }
//
//            public <A> Function1<A, Object> compose(Function1<A, Object> g) {
//                return super.compose(g);
//            }
//
//            public <C> PartialFunction<Object, C> andThen(Function1<Object, C> k) {
//                return super.andThen(k);
//            }
//
//            public Function1<Object, Option<Object>> lift() {
//                return super.lift();
//            }
//        });
        _system.shutdown();
    }
}
