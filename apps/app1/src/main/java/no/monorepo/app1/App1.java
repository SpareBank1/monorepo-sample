package no.monorepo.app1;

import no.monorepo.lib1.Lib1;

public class App1
{
    public static void main( String[] args )
    {
        System.out.println( "Hello app1! ");
        System.out.println(args.length > 0 ? Lib1.scream(args[0]) : "NO INPUT");
    }
}
