package compiler.generator;

import compiler.core.Expression;

public class AssemblyGenerator {

	public enum Operation {

		PLUS("+"), MINUS("-"), MULT("*"), DIV("/"), MOD("%"), LE_OP("<="), GE_OP(
				">="), LESS_THAN("<"), MORE_THAN(">"), EQ_OP("=="), NE_OP("!="), AND_OP(
				"&&"), OR_OP("||");

		private String op;

		Operation(String op) {
			this.op = op;
		}

		public String getOp() {
			return this.op;
		}
	}

	private static AssemblyGenerator generator;

	public static AssemblyGenerator getInstance() {
		if (generator == null) {
			generator = new AssemblyGenerator();
		}

		return generator;
	}

	private AssemblyGenerator() {
	}

	public static int labels = 100;
	public static int labelAux;
	public static String assemblyCode = "100: LD SP, 1000\n";

	public static void parseToAssembly(String assemblyString) {
		assemblyCode += assemblyString + "\n";
		System.out.println("ASSEMBLY IS: " + assemblyCode);
	}

	public static void generate(String op, Expression e1, Expression e2) {
		System.out.println("IT arrived with op: " + op + " e1: " + e1 + " e2: "
				+ e2);
		if (op.equals(Operation.PLUS.getOp())) {// +
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": ADD R1, R1, R2");
			labels = labels + 16;
		} else if (op.equals(Operation.MINUS.getOp())) {// -
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": SUB R1, R1, R2");
			labels = labels + 16;
		} else if (op.equals(Operation.MULT.getOp())) {// *
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": MUL R1, R1, R2");
			labels = labels + 16;
		} else if (op.equals(Operation.DIV.getOp())) {// /
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": DIV R1, R1, R2");
			labels = labels + 16;
		} else if (op.equals(Operation.EQ_OP.getOp())) {// ==
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": SUB R1, R1, R2");

			parseToAssembly(labels + 32 + ": BEQZ R1, " + (labels + 56)); // if
			parseToAssembly(labels + 40 + ": LD R1, #0"); // else
			parseToAssembly(labels + 48 + ": BR " + (labels + 64)); // else
			parseToAssembly((labels + 56) + ": LD R1, #1"); // if
			parseToAssembly(labels + 64 + ":"); // else
			labels = labels + 64;
		}

		else if (op.equals(Operation.NE_OP.getOp())) {// !=
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": SUB R1, R1, R2");
			parseToAssembly(labels + 32 + ": BEQZ R1, " + (labels + 56)); // if
			parseToAssembly(labels + 40 + ": LD R1, #1"); // else
			parseToAssembly(labels + 48 + ": BR " + (labels + 64)); // else
			parseToAssembly((labels + 56) + ": LD R1, #0"); // if
			parseToAssembly(labels + 64 + ":"); // else labels = labels + 64; }
		} else if (op.equals(Operation.AND_OP.getOp())) {// &&
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": BEQZ R1, " + (labels + 56)); // if
			parseToAssembly(labels + 32 + ": BEQZ R2, " + (labels + 56)); // if
			parseToAssembly((labels) + 40 + ": LD R1, #1"); // else
			parseToAssembly(labels + 48 + ": BR " + (labels + 64)); // else
			parseToAssembly((labels + 56) + ": LD R1, #0"); // if
			parseToAssembly(labels + 64 + ":"); // else
			labels = labels + 64;
		} else if (op.equals(Operation.OR_OP.getOp())) { // ||
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": BNEZ R1, " + (labels + 56)); // if
			parseToAssembly(labels + 32 + ": BNEZ R2, " + (labels + 56)); // if
			parseToAssembly((labels + 40) + ": LD R1, #0"); // else
			parseToAssembly(labels + 48 + ": BR " + (labels + 64)); // else
			parseToAssembly((labels + 56) + ": LD R1, #1"); // if
			parseToAssembly(labels + 64 + ":"); // else
			labels = labels + 64;
		} else if (op.equals(Operation.LESS_THAN.getOp())) {// x - 3 < 0
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": SUB R1, R1, R2");
			parseToAssembly(labels + 32 + ": BLTZ R1, " + (labels + 56)); // if
			parseToAssembly(labels + 40 + ": LD R1, #0"); // else
			parseToAssembly(labels + 48 + ": BR " + (labels + 64)); // else
			parseToAssembly((labels + 56) + ": LD R1, #1"); // if
			parseToAssembly(labels + 64 + ":"); // else
			labels = labels + 64;
		} else if (op.equals(Operation.MORE_THAN.getOp())) {// >
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": SUB R1, R1, R2");
			parseToAssembly(labels + 32 + ": BGTZ R1, " + (labels + 52)); // if
			parseToAssembly(labels + 40 + ": LD R1, #0"); // else
			parseToAssembly(labels + 48 + ": BR " + (labels + 64)); // else
			parseToAssembly((labels + 52) + ": LD R1, #1"); // if
			parseToAssembly(labels + 64 + ":"); // else
			labels = labels + 64;
		} else if (op.equals(Operation.LE_OP.getOp())) {// <=
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": SUB R1, R1, R2");
			parseToAssembly(labels + 32 + ": BLEZ R1, " + (labels + 56)); // if
			parseToAssembly(labels + 40 + ": LD R1, #0"); // else
			parseToAssembly(labels + 48 + ": BR " + (labels + 64)); // else
			parseToAssembly((labels + 56) + ": LD R1, #1"); // if
			parseToAssembly(labels + 64 + ":"); // else
			labels = labels + 64;
		} else if (op.equals(Operation.GE_OP.getOp())) {// >=
			parseToAssembly(labels + ": LD R1, " + e1.getAssemblyValue());
			parseToAssembly(labels + 8 + ": LD R2, " + e2.getAssemblyValue());
			parseToAssembly(labels + 16 + ": SUB R1, R1, R2");
			parseToAssembly(labels + 32 + ": BGEZ R1, " + (labels + 56)); // if
			parseToAssembly(labels + 40 + ": LD R1, #0"); // else
			parseToAssembly(labels + 48 + ": BR " + (labels + 64)); // else
			parseToAssembly((labels + 56) + ": LD R1, #1"); // if
			parseToAssembly(labels + 64 + ":"); // else
			labels = labels + 64;
		}

	}
}
