using GeneticSharp.Domain.Chromosomes;
using System;
using System.Linq;
using UnityEngine;
using GeneticSharp.Domain.Randomizations;
using System.Collections.Generic;
using GeneticSharp.Domain.Crossovers;

namespace GeneticSharp.Runner.UnityApp.Commons
{
    public class Crossover : ICrossover
    {
  

        

        public int ParentsNumber { get; private set; }

        public int ChildrenNumber { get; private set; }

        public int MinChromosomeLength { get; private set; }

        public bool IsOrdered { get; private set; } // indicating whether the operator is ordered (if can keep the chromosome order).

        protected float crossoverProbability;


        public Crossover(float crossoverProbability) : this(2, 2, 2, true)
        {
            this.crossoverProbability = crossoverProbability;
        }

        public Crossover(int parentsNumber, int offSpringNumber, int minChromosomeLength, bool isOrdered)
        {
            ParentsNumber = parentsNumber;
            ChildrenNumber = offSpringNumber;
            MinChromosomeLength = minChromosomeLength;
            IsOrdered = isOrdered;
        }

        public IList<IChromosome> Cross(IList<IChromosome> parents)
        {
            IChromosome parent1 = parents[0];
            IChromosome parent2 = parents[1];
            IChromosome offspring1 = parent1.Clone();
            IChromosome offspring2 = parent2.Clone();

            /* YOUR CODE HERE */
            /*REPLACE THESE LINES BY YOUR CROSSOVER IMPLEMENTATION*/

            // Gera um valor aleatório entre 0 e 1
            var randomValue = RandomizationProvider.Current.GetDouble();

            // Verifica se esta a baixo da probabilidade
            if ( randomValue <= crossoverProbability){

                // Gera um índice aleatório para determinar onde ocorrerá o crossover
                int index_crossover = RandomizationProvider.Current.GetInt(0, parent1.Length);

                // Existe a possibilidade de trocar de pais a partir do índice de crossover
                for (int i = index_crossover; i < parent1.Length; i++){

                     // Verifica se um novo crossover deve ocorrer
                    if (RandomizationProvider.Current.GetDouble() < 0.5)
                    {
                        // Se o valor aleatório for menor que 0.5, os genes dos pais são trocados nos descendentes
                        offspring1.ReplaceGene(i, parent1.GetGene(i));
                        offspring2.ReplaceGene(i, parent2.GetGene(i));
                    }
                    
                    else
                    {
                        // Caso contrário, os genes são trocados de forma inversa
                        offspring1.ReplaceGene(i, parent2.GetGene(i));
                        offspring2.ReplaceGene(i, parent1.GetGene(i));
                    }

                }
            }


            return new List<IChromosome> { offspring1, offspring2 };
            
        }
    }
}